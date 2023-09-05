package io.github.folkly.guppi.common.diff.ui;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestPanel;
import com.intellij.diff.contents.DocumentContent;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import io.github.folkly.guppi.common.diff.util.DiffStringsDialogBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public class DiffStringsDialog extends DialogWrapper {

    public final @NotNull Project project;
    public final @NotNull String leftStr;
    public final @NotNull String rightStr;
    public final @Nullable FileType fileType;
    public final @NotNull DocumentContent leftDoc;
    public final @NotNull DocumentContent rightDoc;
    public @Nullable String title;
    public @Nullable String leftTitle;
    public @Nullable String rightTitle;

    public DiffStringsDialog(@NotNull Project project, @NotNull String left, @NotNull String right, @Nullable FileType fileType) {
        super(true); // use current window as parent
        this.project = project;
        this.leftStr = left;
        this.rightStr = right;
        this.fileType = fileType;
        this.leftDoc = DiffContentFactory.getInstance().create(leftStr, fileType);
        this.rightDoc = DiffContentFactory.getInstance().create(rightStr, fileType);
    }

    public void setLeftReadOnly(boolean isReadOnly) {
        leftDoc.getDocument().setReadOnly(isReadOnly);
    }

    public void setRightReadOnly(boolean isReadOnly) {
        rightDoc.getDocument().setReadOnly(isReadOnly);
    }

    public Optional<String> diff() {
        init();

        boolean result = showAndGet();
        if (result) {
            return getModifiedString();
        } else {
            return Optional.empty();
        }
    }

    public static DiffStringsDialogBuilder builder(@NotNull Project project, @NotNull String left, @NotNull String right) {
        return new DiffStringsDialogBuilder(project, left, right);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        DiffRequest diffRequest = new SimpleDiffRequest(title, leftDoc, rightDoc, leftTitle, rightTitle);
        DiffRequestPanel diffRequestPanel = DiffManager.getInstance().createRequestPanel(project, this.myDisposable, null);
        diffRequestPanel.setRequest(diffRequest);
        return diffRequestPanel.getComponent();
    }

    protected Optional<String> getModifiedString() {
        if (isModified()) {
            if (isLeftModified()) {
                return Optional.of(getLeftText());
            } else {
                return Optional.of(getRightText());
            }
        } else {
            return Optional.empty();
        }
    }

    protected boolean isModified() {
        return isLeftModified() || isRightModified();
    }

    protected boolean isLeftModified() {
        return !leftStr.equals(getLeftText());
    }

    protected boolean isRightModified() {
        return !rightStr.equals(getRightText());
    }

    protected String getLeftText() {
        return leftDoc.getDocument().getText();
    }

    protected String getRightText() {
        return rightDoc.getDocument().getText();
    }

}
