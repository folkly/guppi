package io.github.folkly.guppi.common.context;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrentFileContext {

    protected final Editor editor;

    public CurrentFileContext(@NotNull Editor editor) {
        this.editor = editor;
    }

    public int getCaretOffset() {
        return editor.getCaretModel().getOffset();
    }

    public boolean hasSelection() {
        return editor.getSelectionModel().hasSelection();
    }

    public int getSelectionStartOffset() {
        return editor.getSelectionModel().getSelectionStart();
    }

    public int getSelectionEndOffset() {
        return editor.getSelectionModel().getSelectionEnd();
    }

    public String getText() {
        return editor.getDocument().getText();
    }

    public String getSelectedText() {
        return editor.getSelectionModel().getSelectedText();
    }

    public String getSystemMessageText() {
        return String.format("""
                Below are the contents of the current file.

                ```
                %s
                ```

                %s
                """, getText(), getSystemMessageMagicStringSection());
    }

    protected String getSystemMessageMagicStringSection() {
        if (hasSelection()) {
            return String.format("""
                    The user's caret is currently at offset %s.
                    The user's select text is currently from offsets %s to %s.
                    """, getCaretOffset(), getSelectionStartOffset(), getSelectionEndOffset());
        } else {
            return String.format("""
                    The user's caret is currently at offset %s.
                    """, getCaretOffset());
        }
    }

}
