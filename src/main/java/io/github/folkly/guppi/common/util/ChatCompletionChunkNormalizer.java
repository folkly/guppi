package io.github.folkly.guppi.common.util;

import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import io.github.folkly.openai.model.v1.chat.completions.stream.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChatCompletionChunkNormalizer {
    protected final ChatCompletionChunk original;

    public ChatCompletionChunkNormalizer(ChatCompletionChunk original) {
        this.original = original;
    }

    public List<ChatCompletionChunk> normalize() {
        return splitLines(splitChoices(original));
    }

    public static List<ChatCompletionChunk> normalize(ChatCompletionChunk chunk) {
        return new ChatCompletionChunkNormalizer(chunk).normalize();
    }

    public static List<ChatCompletionChunk> normalize(List<ChatCompletionChunk> chunks) {
        List<ChatCompletionChunk> normalized = new ArrayList<>();
        for (ChatCompletionChunk chunk : chunks) {
            normalized.addAll(normalize(chunk));
        }
        return normalized;
    }

    protected static List<ChatCompletionChunk> splitChoices(ChatCompletionChunk chunk) {
        List<ChatCompletionChunk> chunks = new ArrayList<>();

        for (Choice choice : chunk.choices) {
            ChatCompletionChunk newChunk = ChatCompletionChunkBuilder.from(chunk)
                    .clearChoices()
                    .withChoice(choice)
                    .build();
            chunks.add(newChunk);
        }

        return chunks;
    }

    protected static List<ChatCompletionChunk> splitLines(List<ChatCompletionChunk> chunks) {
        List<ChatCompletionChunk> newChunks = new ArrayList<>();
        for (ChatCompletionChunk chunk : chunks) {
            newChunks.addAll(splitLines(chunk));
        }
        return newChunks;
    }

    protected static List<ChatCompletionChunk> splitLines(ChatCompletionChunk chunk) {
        List<ChatCompletionChunk> chunks = new ArrayList<>();

        Choice choice = chunk.choices.get(0);
        String content = chunk.choices.get(0).delta.content;

        if (!content.contains("\n") || content.equals("\n")) {
            return List.of(chunk);
        }

        String[] lines = content.split("\n", -1);

        ChatCompletionChunk newlineChunk = ChatCompletionChunkBuilder.from(chunk)
                .clearChoices()
                .withChoice(new Choice(choice.index, "\n"))
                .build();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Choice lineChoice = new Choice(choice.index, line);

            ChatCompletionChunk lineChunk = ChatCompletionChunkBuilder.from(chunk)
                    .clearChoices()
                    .withChoice(lineChoice)
                    .build();
            chunks.add(lineChunk);

            if (i < lines.length - 1) {
                chunks.add(newlineChunk);
            }
        }

        chunks.get(chunks.size() - 1).choices.get(0).finish_reason = choice.finish_reason;

        return chunks;
    }
}
