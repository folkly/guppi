package io.github.folkly.guppi.integration.openai;

import io.github.folkly.openai.model.v1.embeddings.Embeddings;
import io.github.folkly.openai.model.v1.embeddings.EmbeddingsData;

import java.io.IOException;
import java.util.List;

public class OpenAiEmbeddings {

    protected final OpenAiApi api;

    protected static final String DEFAULT_MODEL = "text-embedding-ada-002";

    public OpenAiEmbeddings() {
        this(OpenAi.getApi());
    }

    public OpenAiEmbeddings(OpenAiApi api) {
        this.api = api;
    }

    public Embeddings createEmbedding(String input) throws IOException {
        return createEmbeddings(List.of(input));
    }

    public Embeddings createEmbeddings(List<String> inputs) throws IOException {

        EmbeddingsData data = EmbeddingsData.builder()
                .model(DEFAULT_MODEL)
                .input(inputs)
                .build();

        return api.v1().postEmbeddings(data);
    }

}
