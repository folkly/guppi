package io.github.folkly.guppi.common.ui.popup;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.popup.ComponentPopupBuilderImpl;
import io.github.folkly.guppi.common.ui.panel.RoundedCompoundPanel;
import io.github.folkly.guppi.common.util.DimensionWrapper;
import io.github.folkly.guppi.common.ui.field.PromptField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class PromptPopup extends RoundedCornerPopup {

    protected PromptField promptField;
    protected Consumer<String> promptHandler;

    public PromptPopup(@NotNull Project project, @NotNull Editor editor, RoundedCompoundPanel panel, Dimension minSize, PromptField promptField, Consumer<String> promptHandler) {
        super(project, editor, panel, minSize);
        this.promptField = promptField;
        this.promptHandler = promptHandler;
    }

    @Override
    public boolean dispatchKeyEvent(@NotNull KeyEvent e) {
        Dimension size = DimensionWrapper.from(promptField.getPreferredSize()).add(rootPanel.totalBorderThickness).dim();
        setSize(size);
        if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
            promptHandler.accept(promptField.getText());
            closeOk(e);
            return true;
        }
        return super.dispatchKeyEvent(e);
    }

    public static PromptPopup create(@NotNull Project project, @NotNull Editor editor, Consumer<String> promptHandler) {

        RoundedCompoundPanel root = new RoundedCompoundPanel(project, editor);

        PromptField promptField = new PromptField(project, editor);
        promptField.setOpaque(false);

        JBScrollPane scrollPane = new JBScrollPane(promptField);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setHorizontalScrollBarPolicy(JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);

        root.add(scrollPane);

        ComponentPopupBuilderImpl builder = createComponentPopupBuilderImpl(root, promptField, editor, promptField.getMinimumSize());

        return (PromptPopup) builder
                .createPopup(() -> new PromptPopup(project, editor, root, promptField.getMinimumSize(), promptField, promptHandler));
    }
}
