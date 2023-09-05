package io.github.folkly.guppi.chat;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.folkly.guppi.chat.ui.QuickChatPanel;
import org.jetbrains.annotations.NotNull;

public class QuickChatToolWindowFactory implements ToolWindowFactory {

    public static final String ID = "Guppi";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        Editor editor = fileEditorManager.getSelectedTextEditor();
        assert editor != null;

        QuickChatPanel quickChatPanel = new QuickChatPanel(project, editor);
        Content content = ContentFactory.getInstance().createContent(quickChatPanel, "Quick Chat", false);
        toolWindow.getContentManager().addContent(content);
    }
}
