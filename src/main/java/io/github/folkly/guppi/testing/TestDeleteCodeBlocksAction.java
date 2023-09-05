package io.github.folkly.guppi.testing;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestDeleteCodeBlocksAction extends AnAction {

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

//        PsiFile psiFile = (PsiFile) PsiDocumentManager.getInstance(project).getPsiFile(document).copy();
//
//        deleteCodeBlocks(psiFile);
//
//        System.out.println(psiFile.getText());

        System.out.println();
    }

    protected static void deleteCodeBlocks(PsiElement psiElement) {
        List<PsiElement> toDelete = new ArrayList<>();

        psiElement.accept(new PsiRecursiveElementWalkingVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                // Filter out code blocks
                // PyFunction
                String className = e.getClass().getName();
                if (className.matches(".*PsiCodeBlock.*")) {
                    toDelete.add(e);
                } else if (className.matches(".*PyFunction.*")) {
//                    if ((PyFunction))
                    System.out.println(e);
                } else {
//                    System.out.println(e);
//                    System.out.println();
//                    System.out.println(e.getText());
//                    System.out.println();

                    super.visitElement(e);
                }
            }
        });

        for (PsiElement e : toDelete) {
            e.delete();
        }
    }

}
