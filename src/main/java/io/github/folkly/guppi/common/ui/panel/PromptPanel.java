package io.github.folkly.guppi.common.ui.panel;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.common.ui.field.PromptField;
import org.jetbrains.annotations.NotNull;

public class PromptPanel extends RoundedCompoundPanel {

    public final PromptField field;

    public PromptPanel(@NotNull Project project, @NotNull Editor editor) {
        super(project, editor);
        this.field = new PromptField(project, editor);

        add(field);
    }

    public PromptPanel(@NotNull Project project, @NotNull Editor editor, int arcSize, int innerBorderThickness, int outerBorderThickness) {
        super(project, editor, arcSize, innerBorderThickness, outerBorderThickness);
        this.field = new PromptField(project, editor);

        add(field);
    }
}
