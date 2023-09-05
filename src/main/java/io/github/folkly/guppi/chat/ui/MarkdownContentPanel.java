package io.github.folkly.guppi.chat.ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.RoundedLineBorder;
import io.github.folkly.guppi.chat.ui.controllers.AssistantMessagePanelController;
import io.github.folkly.guppi.chat.ui.controllers.MarkdownContentPanelController;
import io.github.folkly.guppi.common.ui.field.LanguageField;
import org.jetbrains.annotations.NotNull;

public class MarkdownContentPanel extends ContentPanel {

    public final MarkdownContentPanelController controller;

    public MarkdownContentPanel(@NotNull Project project, @NotNull Editor editor, AssistantMessagePanelController parentController) {
        super(project, editor, LanguageField.MARKDOWN, parentController);
        this.controller = new MarkdownContentPanelController(project, editor, parentController, this);

        setBorder(new RoundedLineBorder(getDefaultEditorBackgroundColor(), 10, 2));
    }
}
