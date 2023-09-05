package io.github.folkly.guppi.common.ui.popup;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.popup.AbstractPopup;
import com.intellij.ui.popup.ComponentPopupBuilderImpl;
import io.github.folkly.guppi.common.ui.panel.RoundedCompoundPanel;
import io.github.folkly.guppi.common.util.DimensionWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a rounded corner popup displayed in an editor.
 */
public class RoundedCornerPopup extends AbstractPopup {

    public final Project project;
    public final Editor editor;
    public final RoundedCompoundPanel rootPanel;
    public final Dimension minSize;
    public Dimension maxSize;

    public RoundedCornerPopup(@NotNull Project project, @NotNull Editor editor, RoundedCompoundPanel panel, Dimension minSize) {
        this.project = project;
        this.editor = editor;
        this.rootPanel = panel;
        this.minSize = minSize;
    }

    public RoundedCornerPopup setMaxSize(Dimension maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public void showCenteredInEditorContent() {
        show(getCenteredRelativePoint());
    }

    public void showUpperCenteredInEditorContent() {
        show(getUpperCenteredRelativePoint());
    }

    public void resizeAsync() {
        ApplicationManager.getApplication().invokeLater(this::resize);
    }

    public void resize() {
        Dimension size = DimensionWrapper.from(getPreferredContentSize())
                .clampMin(minSize)
                .clampMax(maxSize)
                .dim();
        setSize(size);
    }

    public static RoundedCornerPopup create(@NotNull Project project, @NotNull Editor editor) {
        DimensionWrapper parentSize = DimensionWrapper.from(editor.getContentComponent().getSize());
        return create(project, editor, parentSize.scaleWidth(0.5).scaleHeight(0.1).dim());
    }

    public static RoundedCornerPopup create(@NotNull Project project, @NotNull Editor editor, Dimension minSize) {
        RoundedCompoundPanel panel = new RoundedCompoundPanel(project, editor);

        ComponentPopupBuilderImpl builder = createComponentPopupBuilderImpl(panel, panel, editor, minSize);

        return (RoundedCornerPopup) builder
                .createPopup(() -> new RoundedCornerPopup(project, editor, panel, minSize));
    }

    public static ComponentPopupBuilderImpl createComponentPopupBuilderImpl(@NotNull JComponent content,
                                                                            @Nullable JComponent preferableFocusComponent,
                                                                            @NotNull Editor editor,
                                                                            Dimension minSize) {
        return (ComponentPopupBuilderImpl) JBPopupFactory.getInstance()
                .createComponentPopupBuilder(content, preferableFocusComponent)
                .setMovable(true)
                .setRequestFocus(true)
                .setResizable(true)
                .setShowBorder(false)
                .setMinSize(minSize);

    }

    protected RelativePoint getCenteredRelativePoint() {
        Dimension size = getSize() == null ? minSize : getSize();
        Dimension editorSize = editor.getContentComponent().getSize();

        int x = (editorSize.width - size.width) / 2;
        int y = (editorSize.height - size.height) / 2;

        Point point = new Point(x, y);

        return new RelativePoint(editor.getContentComponent(), point);
    }

    protected RelativePoint getUpperCenteredRelativePoint() {
        Dimension size = getSize() == null ? minSize : getSize();
        Dimension editorSize = editor.getContentComponent().getSize();

        int x = (editorSize.width - size.width) / 2;
        int y = 0;

        Point point = new Point(x, y);

        return new RelativePoint(editor.getContentComponent(), point);
    }
}
