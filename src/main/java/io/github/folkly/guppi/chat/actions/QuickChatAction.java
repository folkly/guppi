package io.github.folkly.guppi.chat.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.guppi.chat.QuickChatToolWindowFactory;
import io.github.folkly.guppi.chat.ui.QuickChatPanel;
import io.github.folkly.guppi.chat.ui.QuickChatPopup;
import io.github.folkly.guppi.common.context.CurrentFileContext;
import io.github.folkly.guppi.common.context.SystemContext;
import io.github.folkly.guppi.common.ui.popup.PromptPopup;
import io.github.folkly.guppi.integration.openai.OpenAi;
import io.github.folkly.openai.api.Api;
import io.github.folkly.openai.model.v1.chat.completions.post.ChatCompletionsData;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import org.jetbrains.annotations.NotNull;

public class QuickChatAction extends AnAction {

    Project project;
    Editor editor;
    PromptPopup promptPopup;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        assert editor != null;

        this.project = project;
        this.editor = editor;

        promptPopup = PromptPopup.create(project, editor, this::handlePromptInput);
        promptPopup.showInCenterOf(editor.getContentComponent());
    }

    protected void handlePromptInput(String input) {

        promptPopup.closeOk(null);

        // Get the tool window manager
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

        // Show or focus on the tool window
        ToolWindow toolWindow = toolWindowManager.getToolWindow(QuickChatToolWindowFactory.ID);

        assert toolWindow != null;

        // If the tool window is not visible, show it
        if (!toolWindow.isVisible()) {
            toolWindow.show(null);
        }

        QuickChatPanel quickChatPanel = (QuickChatPanel) toolWindow.getContentManager().getContent(0).getComponent();

        CurrentFileContext currentFileContext = new CurrentFileContext(editor);

        Api api = OpenAi.getApi();

        ChatCompletionsData data = ChatCompletionsData.builder()
                .stream()
                .model("gpt-3.5-turbo")
                .systemMessage(SystemContext.MESSAGE_1)
                .systemMessage(currentFileContext.getSystemMessageText())
                .userMessage(input)
                .build();

        try {
            AutoCloseableStreamWrapper<ChatCompletionChunk> chatCompletions = api.v1().streamChatCompletions(data);
            quickChatPanel.controller.handleStream(chatCompletions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
