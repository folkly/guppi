package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.guppi.chat.ui.QuickChatPanel;
import io.github.folkly.guppi.common.util.BaseController;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import org.jetbrains.annotations.NotNull;

public class QuickChatPanelController extends BaseController {

    public final QuickChatPanel panel;

    public QuickChatPanelController(@NotNull Project project, @NotNull Editor editor, @NotNull QuickChatPanel panel) {
        super(project, editor);
        this.panel = panel;
    }

    public void handleStream(AutoCloseableStreamWrapper<ChatCompletionChunk> stream) {
        panel.assistantMessagePanel.controller.clear();
        panel.assistantMessagePanel.controller.handleStreamAsync(stream);
    }
}
