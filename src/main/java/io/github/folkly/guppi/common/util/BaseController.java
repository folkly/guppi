package io.github.folkly.guppi.common.util;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class BaseController {
    @NotNull
    public final Project project;
    @NotNull
    public final Editor editor;

    public BaseController(@NotNull Project project, @NotNull Editor editor) {
        this.project = project;
        this.editor = editor;
    }
}
