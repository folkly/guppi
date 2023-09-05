package io.github.folkly.guppi.common.util;

import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import io.github.folkly.openai.model.v1.chat.completions.stream.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChatCompletionChunkBuilder {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;

    public ChatCompletionChunkBuilder() {
    }

    public ChatCompletionChunkBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ChatCompletionChunkBuilder withObject(String object) {
        this.object = object;
        return this;
    }

    public ChatCompletionChunkBuilder withCreated(Long created) {
        this.created = created;
        return this;
    }

    public ChatCompletionChunkBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public ChatCompletionChunkBuilder clearChoices() {
        this.choices = new ArrayList<>();
        return this;
    }

    public ChatCompletionChunkBuilder withChoices(List<Choice> choices) {
        if (this.choices == null) {
            this.choices = new ArrayList<>();
        }
        this.choices.addAll(choices);
        return this;
    }

    public ChatCompletionChunkBuilder withChoice(Choice choice) {
        if (this.choices == null) {
            this.choices = new ArrayList<>();
        }
        this.choices.add(choice);
        return this;
    }

    public ChatCompletionChunk build() {
        return new ChatCompletionChunk(id, object, created, model, choices);
    }

    public static ChatCompletionChunkBuilder from(ChatCompletionChunk chunk) {
        return new ChatCompletionChunkBuilder()
                .withId(chunk.id)
                .withObject(chunk.object)
                .withCreated(chunk.created)
                .withModel(chunk.model)
                .withChoices(chunk.choices);
    }
}
