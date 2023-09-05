package io.github.folkly.guppi.common.ui.panel;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.RoundedLineBorder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RoundedCompoundPanel extends BasePanel {

    public final BasePanel secondary;
    public final int arcSize;
    public final int innerBorderThickness;
    public final int outerBorderThickness;
    public final int totalBorderThickness;

    public RoundedCompoundPanel(@NotNull Project project, @NotNull Editor editor) {
        this(project, editor, 10, 5, 2);
    }

    public RoundedCompoundPanel(@NotNull Project project, @NotNull Editor editor, int arcSize, int innerBorderThickness, int outerBorderThickness) {
        super(project, editor);
        this.arcSize = arcSize;
        this.innerBorderThickness = innerBorderThickness;
        this.outerBorderThickness = outerBorderThickness;
        this.totalBorderThickness = innerBorderThickness + outerBorderThickness;

        setBackground(getDefaultEditorBackgroundColor());
        setBorder(new RoundedLineBorder(getDefaultBorderColor(), arcSize, outerBorderThickness));
        setLayout(new BorderLayout());
        setOpaque(true);

        secondary = new BasePanel(project, editor);
        secondary.setBackground(null);
        secondary.setBorder(new RoundedLineBorder(getDefaultEditorBackgroundColor(), arcSize, innerBorderThickness));
        secondary.setLayout(new BorderLayout());
        secondary.setOpaque(false);

        super.add(secondary, BorderLayout.CENTER);
    }

    public Component add(Component component) {
        secondary.add(component, BorderLayout.CENTER);
        return component;
    }

    public void add(@NotNull Component component, Object constraints) {
        secondary.add(component, constraints);
    }
}
