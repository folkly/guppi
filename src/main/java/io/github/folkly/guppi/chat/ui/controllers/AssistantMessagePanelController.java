package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.lang.Language;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.uiDesigner.core.Spacer;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.guppi.chat.ui.AssistantMessagePanel;
import io.github.folkly.guppi.chat.ui.CodeContentPanel;
import io.github.folkly.guppi.chat.ui.ContentPanel;
import io.github.folkly.guppi.chat.ui.MarkdownContentPanel;
import io.github.folkly.guppi.common.ui.field.LanguageField;
import io.github.folkly.guppi.common.util.BaseController;
import io.github.folkly.guppi.common.util.ChatCompletionChunkIterable;
import io.github.folkly.guppi.common.util.ChatCompletionChunkIterator;
import io.github.folkly.guppi.common.util.GridBagConstraintsBuilder;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AssistantMessagePanelController extends BaseController {

    public final QuickChatPanelController parent;
    public AssistantMessagePanel panel;
    public List<ContentPanel> sections;
    public final Application application;

    public AssistantMessagePanelController(@NotNull Project project, @NotNull Editor editor, AssistantMessagePanel panel, QuickChatPanelController parent) {
        super(project, editor);
        this.parent = parent;
        this.panel = panel;
        this.sections = new ArrayList<>();
        this.application = ApplicationManager.getApplication();
    }

    public void clear() {
        sections.clear();
        panel.removeAll();
    }

    public void handleStreamAsync(AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper) {
        ProgressManager progressManager = ProgressManager.getInstance();

        Task.Backgroundable task = new Task.Backgroundable(project, "Guppi") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                handleStream(autoCloseableStreamWrapper);
            }
        };

        progressManager.run(task);
    }

    public void handleStream(AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper) {

        addSpacer();
        addMarkdownSectionInDispatchThread();

        ChatCompletionChunkIterable iterable = new ChatCompletionChunkIterable(autoCloseableStreamWrapper);
        ChatCompletionChunkIterator iter = iterable.iterator();

        while (iter.hasNext()) {
            ChatCompletionChunk chunk = iter.next();
            String content = chunk.choices.get(0).delta.content;

            if (iter.isEnteringCodeBlock()) {
                removeLastLineInDispatchThread();
                trimLastSectionInDispatchThread();
                addCodeSectionInDispatchThread(iter.getLanguage());

                if (content.equals("\n")) {
                    continue;
                }
            }

            if (iter.isExitingCodeBlock()) {
                removeLastLineInDispatchThread();
                trimLastSectionInDispatchThread();
                addMarkdownSectionInDispatchThread();

                if (content.equals("\n")) {
                    continue;
                }
            }

            appendContentInDispatchThread(content);
        }
    }

    public void addSpacer() {
        GridBagConstraints constraints = GridBagConstraintsBuilder.builder()
                .anchor(GridBagConstraints.SOUTH)
                .fill(GridBagConstraints.BOTH)
                .gridx(0)
                .gridy(100)
                .gridwidth(1)
                .gridheight(1)
                .weightx(1)
                .weighty(1)
                .build();
        panel.add(new Spacer(), constraints);
    }

    public void addMarkdownSectionInDispatchThread() {
        application.invokeAndWait(() -> {
            addMarkdownSection();
        });
    }

    public void addCodeSectionInDispatchThread(Language language) {
        application.invokeAndWait(() -> {
            addCodeSection(language);
        });
    }

    public void appendContentInDispatchThread(String s) {
        application.invokeAndWait(() -> {
            appendContent(s);
        });
    }

    public void removeLastLineInDispatchThread() {
        application.invokeAndWait(() -> {
            removeLastLine();
        });
    }

    public void trimLastSectionInDispatchThread() {
        application.invokeAndWait(() -> {
            trimLastSection();
        });
    }

    public void addMarkdownSection() {
        MarkdownContentPanel section = new MarkdownContentPanel(project, editor, this);
        int index = sections.size();

        sections.add(section);
        panel.add(section, getConstraints(index));
    }

    public void addCodeSection(Language language) {
        CodeContentPanel section = new CodeContentPanel(project, editor, language, this);
        int index = sections.size();

        sections.add(section);
        panel.add(section, getConstraints(index));
    }

    public void appendContent(String s) {
        ContentPanel section = sections.get(sections.size() - 1);
        LanguageField field = section.field;
        Document document = field.getDocument();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.insertString(document.getTextLength(), s);
        });
    }

    protected void removeLastLine() {
        ContentPanel section = sections.get(sections.size() - 1);
        LanguageField field = section.field;
        Document document = field.getDocument();

        int lineCount = document.getLineCount();
        int start = document.getLineStartOffset(lineCount - 1);
        int end = document.getTextLength();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.deleteString(start, end);
        });
    }

    protected void trimLastSection() {
        ContentPanel section = sections.get(sections.size() - 1);
        LanguageField field = section.field;
        Document document = field.getDocument();

        if (document.getLineCount() == 0) {
            return;
        }

        int firstLineStart = document.getLineStartOffset(0);
        int firstLineEnd = document.getLineEndOffset(0);
        String firstLine = document.getText(new TextRange(firstLineStart, firstLineEnd));

        if (firstLine.isBlank()) {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.deleteString(firstLineStart, firstLineEnd + 1);
            });
        }
        if (document.getLineCount() == 0) {
            return;
        }

        int lastLineNumber = document.getLineCount() - 1;
        int lastLineStart = document.getLineStartOffset(lastLineNumber);
        int lastLineEnd = document.getLineEndOffset(lastLineNumber);
        String lastLine = document.getText(new TextRange(lastLineStart, lastLineEnd));

        if (lastLine.isBlank()) {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.deleteString(lastLineStart - 1, lastLineEnd);
            });
        }
    }

    protected static GridBagConstraints getConstraints(int index) {
        return GridBagConstraintsBuilder.builder()
                .anchor(GridBagConstraints.NORTH)
                .fill(GridBagConstraints.HORIZONTAL)
                .gridx(0)
                .gridy(index)
                .gridwidth(1)
                .gridheight(1)
                .weightx(1)
                .build();
    }
}
