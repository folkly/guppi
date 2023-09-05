package io.github.folkly.guppi.testing;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TestAction extends AnAction {

    Project project;
    Editor editor;
    Document document;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;
        this.project = project;

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        this.editor = editor;

        this.document = editor.getDocument();

        LibraryTable libraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);

        for (Library library : libraryTable.getLibraries()) {
            String name = library.getName();
            System.out.println(name);

            VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
            try {
                System.out.println(new String(files[0].contentsToByteArray()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println();
    }

}
