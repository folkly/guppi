package io.github.folkly.guppi.chat.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.chat.ui.controllers.AssistantMessagePanelController;
import io.github.folkly.guppi.chat.ui.controllers.QuickChatPanelController;
import io.github.folkly.guppi.common.ui.panel.BasePanel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AssistantMessagePanel extends BasePanel {
    public AssistantMessagePanelController controller;

    public AssistantMessagePanel(@NotNull Project project, @NotNull Editor editor, QuickChatPanelController quickChatPanelController) {
        super(project, editor);
        this.controller = new AssistantMessagePanelController(project, editor, this, quickChatPanelController);

        setBackground(getDefaultEditorBackgroundColor());
        setLayout(new GridBagLayout());
    }
}
