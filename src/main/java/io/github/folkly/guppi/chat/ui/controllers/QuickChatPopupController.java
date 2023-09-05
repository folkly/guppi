package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.guppi.chat.ui.QuickChatPopup;
import io.github.folkly.guppi.common.util.BaseController;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import org.jetbrains.annotations.NotNull;

public class QuickChatPopupController extends BaseController {

    public final QuickChatPopup popup;

    public QuickChatPopupController(@NotNull Project project, @NotNull Editor editor, QuickChatPopup popup) {
        super(project, editor);
        this.popup = popup;
    }

    public void handleStream(AutoCloseableStreamWrapper<ChatCompletionChunk> stream) {
        popup.quickChatPanel.controller.handleStream(stream);
    }
}
