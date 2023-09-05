package io.github.folkly.guppi.completion.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.guppi.common.util.StreamTextToDocument;
import io.github.folkly.guppi.integration.openai.OpenAi;
import io.github.folkly.openai.api.Api;
import io.github.folkly.openai.model.v1.completions.post.Completions;
import io.github.folkly.openai.model.v1.completions.post.CompletionsData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.stream.Stream;

public class CompleteCurrentLineAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        assert editor != null;

        Api api = OpenAi.getApi();

        // get content of current file before the caret as String
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();
        String fileContentBefore = document.getText(TextRange.create(0, offset));

        // get content of current file after current line
        String fileContentAfter = document.getText(TextRange.create(offset, document.getTextLength()));

        CompletionsData completionsData = CompletionsData.builder()
                .stream()
                .model("text-davinci-003")
                .prompt(fileContentBefore)
                .stop("\n")
                .suffix(fileContentAfter)
                .build();

        AutoCloseableStreamWrapper<Completions> completions;
        try {
            completions = api.v1().streamCompletions(completionsData);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Stream<String> stream = completions.stream().map(c -> c.choices.get(0).text);
        StreamTextToDocument streamTextToDocument = new StreamTextToDocument(project, completions, stream, document, offset);
        streamTextToDocument.run();
    }
}
