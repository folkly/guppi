package io.github.folkly.guppi.integration.openai;

import io.github.folkly.guppi.settings.SettingsState;
import io.github.folkly.openai.api.ApiConfig;

public class OpenAi {
    public static OpenAiApi getApi() {
        return getApi(SettingsState.getInstance().openAiSettingsState.apiKey);
    }

    public static OpenAiApi getApi(String apiKey) {
        return new OpenAiApi(ApiConfig.builder().apiKey(apiKey).build());
    }
}
