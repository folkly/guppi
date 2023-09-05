package io.github.folkly.guppi.common.diff.util;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.common.diff.ui.DiffStringsDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiffStringsDialogBuilder {

    public DiffStringsDialogBuilder(@NotNull Project project, @NotNull String left, @NotNull String right) {
        this.project = project;
        this.leftStr = left;
        this.rightStr = right;
        this.isLeftReadOnly = false;
        this.isRightReadOnly = false;
    }

    public DiffStringsDialogBuilder fileType(@Nullable FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public DiffStringsDialogBuilder fileType(@Nullable String fileExtension) {
        if (fileExtension != null) {
            this.fileType = FileTypeManager.getInstance().getFileTypeByExtension(fileExtension);
        }
        return this;
    }

    public DiffStringsDialogBuilder title(@Nullable String title) {
        this.title = title;
        return this;
    }

    public DiffStringsDialogBuilder leftTitle(@Nullable String leftTitle) {
        this.leftTitle = leftTitle;
        return this;
    }

    public DiffStringsDialogBuilder rightTitle(@Nullable String rightTitle) {
        this.rightTitle = rightTitle;
        return this;
    }

    public DiffStringsDialogBuilder leftReadOnly(boolean isLeftReadOnly) {
        this.isLeftReadOnly = isLeftReadOnly;
        return this;
    }

    public DiffStringsDialogBuilder leftReadOnly() {
        return leftReadOnly(true);
    }

    public DiffStringsDialogBuilder rightReadOnly(boolean isRightReadOnly) {
        this.isRightReadOnly = isRightReadOnly;
        return this;
    }

    public DiffStringsDialogBuilder rightReadOnly() {
        return rightReadOnly(true);
    }

    public DiffStringsDialog build() {
        DiffStringsDialog d = new DiffStringsDialog(project, leftStr, rightStr, fileType);
        d.title = title;
        d.leftTitle = leftTitle;
        d.rightTitle = rightTitle;
        d.setLeftReadOnly(isLeftReadOnly);
        d.setRightReadOnly(isRightReadOnly);
        return d;
    }

    protected @NotNull Project project;
    protected @NotNull String leftStr;
    protected @NotNull String rightStr;
    protected @Nullable FileType fileType;
    protected @Nullable String title;
    protected @Nullable String leftTitle;
    protected @Nullable String rightTitle;
    protected boolean isLeftReadOnly;
    protected boolean isRightReadOnly;
}
