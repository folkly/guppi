package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.chat.ui.MarkdownContentPanel;
import org.jetbrains.annotations.NotNull;

public class MarkdownContentPanelController extends ContentPanelController {

    public final MarkdownContentPanel panel;

    public MarkdownContentPanelController(@NotNull Project project, @NotNull Editor editor, AssistantMessagePanelController parent, MarkdownContentPanel panel) {
        super(project, editor, parent, panel);
        this.panel = panel;
    }
}
