package io.github.folkly.guppi.common.util;

import com.intellij.lang.Language;
import io.github.folkly.base.api.utils.AutoCloseableStreamWrapper;
import io.github.folkly.openai.model.v1.chat.completions.stream.ChatCompletionChunk;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ChatCompletionChunkIterator implements Iterator<ChatCompletionChunk> {

    public final AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper;

    protected final Stream<ChatCompletionChunk> stream;
    protected final Iterator<ChatCompletionChunk> iterator;
    protected final List<ChatCompletionChunk> chunks;
    protected final List<ChatCompletionChunk> buffer;
    protected boolean inCodeBlock;
    protected boolean enteringCodeBlock;
    protected boolean exitingCodeBlock;
    protected Language language;

    protected static final Pattern PATTERN = Pattern.compile("^```([a-zA-Z]+)?$");

    public ChatCompletionChunkIterator(AutoCloseableStreamWrapper<ChatCompletionChunk> autoCloseableStreamWrapper) {
        this.autoCloseableStreamWrapper = autoCloseableStreamWrapper;
        this.stream = autoCloseableStreamWrapper.stream();
        this.iterator = stream.iterator();
        this.chunks = new ArrayList<>();
        this.buffer = new ArrayList<>();
        this.inCodeBlock = false;
    }

    @Override
    public boolean hasNext() {
        if (!buffer.isEmpty() || iterator.hasNext()) {
            return true;
        } else {
            tryClose();
            return false;
        }
    }

    @Override
    public ChatCompletionChunk next() {
        if (!buffer.isEmpty()) {
            ChatCompletionChunk chunk = buffer.remove(0);
            if (chunk.choices.get(0).delta.content.equals("\n") && collectCurrentLine().matches("^```([a-zA-Z]+)?$")) {
                updateCodeBlockDetails(true);
            } else {
                updateCodeBlockDetails(false);
            }
            chunks.add(chunk);
            return chunk;
        } else {
            ChatCompletionChunk chunk = getNext();
            buffer.addAll(ChatCompletionChunkNormalizer.normalize(chunk));
            return next();
        }
    }

    public boolean isInCodeBlock() {
        return inCodeBlock;
    }

    public boolean isEnteringCodeBlock() {
        return enteringCodeBlock;
    }

    public boolean isExitingCodeBlock() {
        return exitingCodeBlock;
    }

    public Language getLanguage() {
        return language;
    }

    public String collectCurrentLine() {
        return String.join("", collectCurrentLineChunks().stream().map(c -> c.choices.get(0).delta.content).toList());
    }

    protected void updateCodeBlockDetails(boolean enteringOrExiting) {
        if (enteringOrExiting) {
            inCodeBlock = !inCodeBlock;
            enteringCodeBlock = inCodeBlock;
            exitingCodeBlock = !inCodeBlock;
            if (enteringCodeBlock) {
                language = determineCurrentLanguage();
            }
            if (exitingCodeBlock) {
                language = null;
            }
        } else {
            enteringCodeBlock = false;
            exitingCodeBlock = false;
        }
    }

    protected Language determineCurrentLanguage() {
        String currentLine = collectCurrentLine();
        Matcher matcher = PATTERN.matcher(currentLine);
        if (matcher.find()) {
            return LanguageUtil.findLanguageById(matcher.group(1));
        } else {
            return null;
        }
    }

    protected ChatCompletionChunk getNext() {
        try {
            return iterator.next();
        } catch (NoSuchElementException e) {
            tryClose();
            throw e;
        }
    }

    protected List<ChatCompletionChunk> collectCurrentLineChunks() {
        List<ChatCompletionChunk> currentLineChunks = new ArrayList<>();
        for (int i = chunks.size() - 1; i >= 0; i--) {
            ChatCompletionChunk chunk = chunks.get(i);
            String content = chunk.choices.get(0).delta.content;
            if (i == chunks.size() - 1) {
                System.err.println();
            } else {
                if (content.contains("\n")) {
                    break;
                }
            }
            currentLineChunks.add(chunk);
        }
        Collections.reverse(currentLineChunks);
        return currentLineChunks;
    }

    protected void tryClose() {
        try {
            autoCloseableStreamWrapper.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
