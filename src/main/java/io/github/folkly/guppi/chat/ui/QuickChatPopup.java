package io.github.folkly.guppi.chat.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.popup.ComponentPopupBuilderImpl;
import io.github.folkly.guppi.chat.ui.controllers.QuickChatPopupController;
import io.github.folkly.guppi.common.ui.panel.RoundedCompoundPanel;
import io.github.folkly.guppi.common.ui.popup.RoundedCornerPopup;
import io.github.folkly.guppi.common.util.DimensionWrapper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class QuickChatPopup extends RoundedCornerPopup {

    public final QuickChatPopupController controller;
    public QuickChatPanel quickChatPanel;

    public QuickChatPopup(@NotNull Project project, @NotNull Editor editor, RoundedCompoundPanel panel, Dimension minSize) {
        super(project, editor, panel, minSize);
        this.controller = new QuickChatPopupController(project, editor, this);
        this.quickChatPanel = new QuickChatPanel(project, editor);

        JBScrollPane scrollPane = new JBScrollPane(quickChatPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);

        rootPanel.add(scrollPane);
    }

    public static QuickChatPopup create(@NotNull Project project, @NotNull Editor editor) {
        EditorColorsScheme colorScheme = editor.getColorsScheme();
        Font font = colorScheme.getFont(EditorFontType.PLAIN);

        DimensionWrapper parentSize = DimensionWrapper.from(editor.getContentComponent().getSize());

        Dimension minSize = new Dimension((int) (0.75 * parentSize.width), font.getSize());

        RoundedCompoundPanel root = new RoundedCompoundPanel(project, editor);

        ComponentPopupBuilderImpl builder = createComponentPopupBuilderImpl(root, root, editor, minSize);
        builder.setCancelOnClickOutside(false);

        QuickChatPopup popup = (QuickChatPopup) builder
                .createPopup(() -> new QuickChatPopup(project, editor, root, minSize));
        return popup;
    }
}
