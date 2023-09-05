package io.github.folkly.guppi.common.util;

import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;
import org.jetbrains.annotations.NotNull;

public class ChatCompletionChunkIterable implements Iterable<ChatCompletionChunk> {

    public final AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper;

    public ChatCompletionChunkIterable(AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper) {
        this.autoCloseableStreamWrapper = autoCloseableStreamWrapper;
    }

    @NotNull
    @Override
    public ChatCompletionChunkIterator iterator() {
        return new ChatCompletionChunkIterator(autoCloseableStreamWrapper);
    }
}
