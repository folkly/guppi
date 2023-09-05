package io.github.folkly.guppi.chat.ui.controllers;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import io.github.folkly.guppi.chat.ui.ContentPanel;
import io.github.folkly.guppi.common.diff.ui.DiffStringsDialog;
import io.github.folkly.guppi.common.util.BaseController;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class ContentPanelController extends BaseController {

    public final AssistantMessagePanelController parent;
    public final ContentPanel panel;

    public ContentPanelController(@NotNull Project project, @NotNull Editor editor, AssistantMessagePanelController parent, ContentPanel panel) {
        super(project, editor);

        this.parent = parent;
        this.panel = panel;
    }

    public void init() {
        panel.field.addMouseListener(new ThisMouseListener(this));
    }

    public void handleMouseClick(MouseEvent e) {
        CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();

        String s = panel.field.getText();

        copyPasteManager.setContents(new StringSelection(s));
    }

    public void handleMouseDoubleClick(MouseEvent e) {
        // get field text
        String s = panel.field.getText();

        // get the Editor for the currently selected file
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        Editor selectedEditor = fileEditorManager.getSelectedTextEditor();

        // insert text into the Editor Document at the current caret offset
        int offset = selectedEditor.getCaretModel().getOffset();
        Document document = selectedEditor.getDocument();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.insertString(offset, s);
        });
    }

    public void handleControlMouseDoubleClick(MouseEvent e) {
        // get field text
        String s = panel.field.getText();

        // get the Editor for the currently selected file
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        Editor selectedEditor = fileEditorManager.getSelectedTextEditor();

        Optional<String> result = DiffStringsDialog.builder(project, s, selectedEditor.getDocument().getText())
                .leftTitle("AI")
                .leftReadOnly(false)
                .rightTitle("User")
                .rightReadOnly(false)
                .build()
                .diff();

        if (result.isPresent()) {
            Document document = selectedEditor.getDocument();

            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.setText(result.get());
            });
        }
    }

    public static class ThisMouseListener implements MouseListener {

        public final ContentPanelController controller;

        public ThisMouseListener(ContentPanelController controller) {
            this.controller = controller;
        }

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                controller.handleMouseClick(e);
            } else if (e.getClickCount() == 2) {
                if (e.isControlDown()) {
                    controller.handleControlMouseDoubleClick(e);
                } else {
                    controller.handleMouseDoubleClick(e);
                }
            }
        }

        /**
         * Invoked when a mouse button has been pressed on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /**
         * Invoked when the mouse enters a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * Invoked when the mouse exits a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}
