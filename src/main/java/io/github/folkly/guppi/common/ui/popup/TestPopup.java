package io.github.folkly.guppi.common.ui.popup;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.popup.AbstractPopup;
import com.intellij.ui.popup.ComponentPopupBuilderImpl;
import com.intellij.util.ui.components.BorderLayoutPanel;
import io.github.folkly.guppi.common.ui.field.PromptField;
import io.github.folkly.guppi.common.ui.panel.RoundedCompoundPanel;
import io.github.folkly.guppi.common.util.DimensionWrapper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import static com.intellij.ide.actions.searcheverywhere.statistics.SearchEverywhereUsageTriggerCollector.DIALOG_CLOSED;

public class TestPopup {

    public static JBPopup create(@NotNull Project project, @NotNull Editor editor) {
        BorderLayoutPanel panel = new BorderLayoutPanel();

        return JBPopupFactory.getInstance().createComponentPopupBuilder(panel, panel)
                .setProject(project)
                .setModalContext(false)
                .setCancelOnClickOutside(true)
                .setRequestFocus(true)
                .addUserData("SIMPLE_WINDOW")
                .setResizable(true)
                .setMovable(true)
                .setLocateWithinScreenBounds(false)
                .setMinSize(new Dimension(300, 300))
                .createPopup();
    }
}
