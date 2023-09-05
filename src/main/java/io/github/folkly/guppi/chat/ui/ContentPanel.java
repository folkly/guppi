package io.github.folkly.guppi.chat.ui;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.chat.ui.controllers.AssistantMessagePanelController;
import io.github.folkly.guppi.chat.ui.controllers.ContentPanelController;
import io.github.folkly.guppi.common.ui.field.LanguageField;
import io.github.folkly.guppi.common.ui.panel.BasePanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends BasePanel {

    public final ContentPanelController controller;
    public final LanguageField field;

    public ContentPanel(@NotNull Project project, @NotNull Editor editor, Language language, AssistantMessagePanelController parentController) {
        super(project, editor);
        this.controller = new ContentPanelController(project, editor, parentController, this);
        this.field = new LanguageField(project, editor, language, true);

        setBackground(getDefaultEditorBackgroundColor());
        setBorder(BorderFactory.createLineBorder(getDefaultEditorBackgroundColor(), 2));
        setLayout(new BorderLayout());

        add(field, BorderLayout.CENTER);

        controller.init();
    }
}
