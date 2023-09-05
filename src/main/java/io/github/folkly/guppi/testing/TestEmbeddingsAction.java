package io.github.folkly.guppi.testing;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import io.github.folkly.guppi.common.ui.popup.PromptPopup;
import io.github.folkly.guppi.integration.openai.OpenAiEmbeddings;
import io.github.folkly.openai.model.v1.embeddings.Embeddings;
import io.github.folkly.openai.util.UsageManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.github.folkly.guppi.embeddings.util.EmbeddingsUtil.calculateDistance;

public class TestEmbeddingsAction extends AnAction {

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
    }

    protected void test() {
        PromptPopup promptPopup = PromptPopup.create(project, editor, this::handlePromptInput);
        promptPopup.showInCenterOf(editor.getContentComponent());
    }

    protected void handlePromptInput(String input) {

        OpenAiEmbeddings api = new OpenAiEmbeddings();

        Embeddings embeddings;
        try {
            embeddings = api.createEmbeddings(List.of(document.getText(), input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        double distance = calculateDistance(embeddings.data.get(0), embeddings.data.get(1));

        System.out.println(UsageManager.getInstance().getEmbeddingsUsage());
        System.out.println(distance);
    }

}
