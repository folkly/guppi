package io.github.folkly.guppi.common.ui.colors;

import com.intellij.openapi.editor.colors.EditorColorsManager;

import java.awt.*;

public class EditorColors {
    public static Color getDefaultBackground() {
        return EditorColorsManager.getInstance().getGlobalScheme().getDefaultBackground();
    }
}
