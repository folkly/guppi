package io.github.folkly.guppi.common.util;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class StreamTextToDocument {

    protected final Project project;
    protected final AutoCloseable autoCloseable;
    protected final Stream<String> stream;
    protected final Document document;
    protected int offset;
    protected List<Consumer<String>> handlers;
    protected List<Consumer<String>> asyncHandlers;

    public StreamTextToDocument(Project project, AutoCloseable autoCloseable, Stream<String> stream, Document document) {
        this(project, autoCloseable, stream, document, 0);
    }

    public StreamTextToDocument(Project project, AutoCloseable autoCloseable, Stream<String> stream, Document document, int offset) {
        this.project = project;
        this.autoCloseable = autoCloseable;
        this.stream = stream;
        this.document = document;
        this.offset = offset;
        this.handlers = new ArrayList<>();
        this.asyncHandlers = new ArrayList<>();
    }

    public StreamTextToDocument addHandler(Consumer<String> handler) {
        handlers.add(handler);
        return this;
    }

    public StreamTextToDocument addAsyncHandler(Consumer<String> handler) {
        asyncHandlers.add(handler);
        return this;
    }

    public void run() {
        Application application = ApplicationManager.getApplication();
        ProgressManager progressManager = ProgressManager.getInstance();
        Runnable runnable = () -> {
            try (autoCloseable) {
                stream.forEach(text -> {
                    insert(text);
                    handlers.forEach(handler -> {
                        handler.accept(text);
                    });
                    asyncHandlers.forEach(handler -> {
                        application.invokeLater(() -> {
                            handler.accept(text);
                        });
                    });
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Task.Backgroundable task = new Task.Backgroundable(project, "Guppi") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                runnable.run();
            }
        };
        progressManager.run(task);
    }

    protected void insert(String text) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.insertString(offset, text);
        });
        offset = offset + text.length();
    }
}
