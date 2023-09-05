package io.github.folkly.guppi.common.ui.field;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.common.util.DimensionWrapper;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PromptField extends LanguageField {

    public PromptField(@NotNull Project project, @NotNull Editor parentEditor) {
        super(project, parentEditor, Language.findLanguageByID("markdown"), false);

        Font font = getParentEditorFont();
        DimensionWrapper parentSize = DimensionWrapper.from(parentEditor.getContentComponent().getSize());

        setPreferredWidth((int) (0.75 * parentSize.width));

        setMinimumSize(new Dimension((int) (0.75 * parentSize.width), font.getSize()));
        setMaximumSize(parentSize.scale(0.9).dim());

        setEnabled(true);
        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension pref = super.getPreferredSize();
        Dimension min = getMinimumSize();
        Dimension max = getMaximumSize();
        Dimension size = DimensionWrapper.from(pref).clampMin(min).clampMax(max).dim();
        return size;
    }
}
