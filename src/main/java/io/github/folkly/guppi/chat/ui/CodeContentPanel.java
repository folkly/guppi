package io.github.folkly.guppi.chat.ui;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.RoundedLineBorder;
import io.github.folkly.guppi.chat.ui.controllers.AssistantMessagePanelController;
import io.github.folkly.guppi.chat.ui.controllers.CodeContentPanelController;
import org.jetbrains.annotations.NotNull;

public class CodeContentPanel extends ContentPanel {

    public final CodeContentPanelController controller;

    public CodeContentPanel(@NotNull Project project, @NotNull Editor editor, Language language, AssistantMessagePanelController parentController) {
        super(project, editor, language, parentController);
        this.controller = new CodeContentPanelController(project, editor, parentController, this);

        setBorder(new RoundedLineBorder(getDefaultBorderColor(), 10, 2));
        field.setBorder(new RoundedLineBorder(getDefaultEditorBackgroundColor(), 10, 6));
    }
}
