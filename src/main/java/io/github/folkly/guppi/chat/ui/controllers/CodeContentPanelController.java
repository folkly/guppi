package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.chat.ui.CodeContentPanel;
import org.jetbrains.annotations.NotNull;

public class CodeContentPanelController extends ContentPanelController {

    public final CodeContentPanel panel;

    public CodeContentPanelController(@NotNull Project project, @NotNull Editor editor, AssistantMessagePanelController parent, CodeContentPanel panel) {
        super(project, editor, parent, panel);
        this.panel = panel;
    }
}
