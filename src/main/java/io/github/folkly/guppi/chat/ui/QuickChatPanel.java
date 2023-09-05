package io.github.folkly.guppi.chat.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.uiDesigner.core.Spacer;
import io.github.folkly.guppi.chat.ui.controllers.QuickChatPanelController;
import io.github.folkly.guppi.common.ui.panel.BasePanel;
import io.github.folkly.guppi.common.util.GridBagConstraintsBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class QuickChatPanel extends BasePanel {

    public final QuickChatPanelController controller;
    public final AssistantMessagePanel assistantMessagePanel;

    public QuickChatPanel(@NotNull Project project, @NotNull Editor editor) {
        super(project, editor);
        this.controller = new QuickChatPanelController(project, editor, this);
        this.assistantMessagePanel = new AssistantMessagePanel(project, editor, controller);

        setBackground(getDefaultEditorBackgroundColor());
        setLayout(new BorderLayout());

        JBScrollPane scrollPane = new JBScrollPane(assistantMessagePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);

        add(scrollPane);
    }
}
