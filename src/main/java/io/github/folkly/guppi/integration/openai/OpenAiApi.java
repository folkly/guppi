package io.github.folkly.guppi.integration.openai;

import io.github.folkly.openai.api.Api;
import io.github.folkly.openai.api.ApiConfig;

public class OpenAiApi extends Api {
    /**
     * Constructs an instance of the OpenAI API using the provided API configuration.
     *
     * @param apiConfig The configuration for the OpenAI API.
     */
    public OpenAiApi(ApiConfig apiConfig) {
        super(apiConfig);
    }
}
