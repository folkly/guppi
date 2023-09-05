package io.github.folkly.guppi.common.ui.colors;

import com.intellij.openapi.editor.colors.EditorColorsManager;

import java.awt.*;

public class Colors {
    public static class Editor {
        public static Color getDefaultBackground() {
            return EditorColorsManager.getInstance().getGlobalScheme().getDefaultBackground();
        }
    }
    public static class Panel {

    }
}
