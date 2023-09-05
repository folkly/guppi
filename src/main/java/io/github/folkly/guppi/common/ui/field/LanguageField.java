package io.github.folkly.guppi.common.ui.field;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LanguageTextField;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class LanguageField extends LanguageTextField {

    public Project project;
    public Editor parentEditor;

    public static final Language MARKDOWN = Language.findLanguageByID("markdown");

    public LanguageField(@NotNull Project project, @NotNull Editor parentEditor, Language language) {
        this(project, parentEditor, language, true);
    }

    public LanguageField(@NotNull Project project, @NotNull Editor parentEditor, Language language, boolean viewer) {
        super(language, project, "", false);

        this.project = project;
        this.parentEditor = parentEditor;

        setFocusable(true);
        setEnabled(true);
        setViewer(viewer);

        setFont(getParentEditorFont());
        setBackground(parentEditor.getColorsScheme().getDefaultBackground());
    }

    public Font getParentEditorFont() {
        EditorColorsScheme colorScheme = parentEditor.getColorsScheme();
        return colorScheme.getFont(EditorFontType.PLAIN);
    }

    @Override
    protected @NotNull EditorEx createEditor() {
        EditorEx ex = super.createEditor();
        ex.setBorder(null);
        ex.getSettings().setUseSoftWraps(true);
        ex.getSettings().setPaintSoftWraps(false);
        ex.getSettings().setCustomSoftWrapIndent(0);
        ex.getSettings().setUseCustomSoftWrapIndent(true);
        return ex;
    }
}
