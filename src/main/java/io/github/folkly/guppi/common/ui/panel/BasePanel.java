package io.github.folkly.guppi.common.ui.panel;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BasePanel extends JBPanel<BasePanel> {

    public final Project project;
    public final Editor editor;

    public BasePanel(@NotNull Project project, @NotNull Editor editor) {
        this.project = project;
        this.editor = editor;
    }

    public Color getDefaultEditorBackgroundColor() {
        return editor.getColorsScheme().getDefaultBackground();
    }

    public Color getDefaultForegroundColor() {
        return editor.getColorsScheme().getDefaultForeground();
    }

    public Color getDefaultBackgroundColor() {
        return UIUtil.getEditorPaneBackground();
    }

    public Color getDefaultBorderColor() {
        return editor.getColorsScheme().getDefaultForeground();
    }

}
