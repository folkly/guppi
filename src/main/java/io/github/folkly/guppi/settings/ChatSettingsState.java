package io.github.folkly.guppi.settings;

public class ChatSettingsState {
    public String primer = "You are an AI pair programming assistant.";
    public long messageLimit = 10;
    public ContextSettings contextSettings = new ContextSettings();

    public static class ContextSettings {
        public boolean includeCurrentDocumentContent = false;
        public boolean includeCurrentSelectedContent = false;
        public boolean includeAllOpenDocumentContent = false;
    }
}
