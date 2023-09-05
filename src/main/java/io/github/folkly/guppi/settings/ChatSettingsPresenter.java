package io.github.folkly.guppi.settings;

public class ChatSettingsPresenter {
    public final ChatSettingsPanel panel;

    public final ChatContextPresenter chatContextPresenter;

    public ChatSettingsPresenter(ChatSettingsPanel panel) {
        this.panel = panel;
        chatContextPresenter = new ChatContextPresenter(panel);
    }

    public boolean isModified(ChatSettingsState settings) {
        boolean primerSame = panel.primer.getText().equals(settings.primer);
        boolean contextSame = !chatContextPresenter.isModified(settings.contextSettings);
        return !(primerSame && contextSame);
    }

    public void apply(ChatSettingsState settings) {
        settings.primer = panel.primer.getText();
        chatContextPresenter.apply(settings.contextSettings);
    }

    public void reset(ChatSettingsState settings) {
        panel.primer.setText(settings.primer);
        chatContextPresenter.reset(settings.contextSettings);
    }

    public static class ChatContextPresenter {
        public final ChatSettingsPanel panel;

        public ChatContextPresenter(ChatSettingsPanel panel) {
            this.panel = panel;
        }

        public boolean isModified(ChatSettingsState.ContextSettings settings) {
            boolean includeCurrentDocumentContentSame = panel.includeCurrentDocumentContent.isSelected() == settings.includeCurrentDocumentContent;
            boolean includeCurrentSelectedContentSame = panel.includeCurrentSelectedContent.isSelected() == settings.includeCurrentSelectedContent;
            boolean includeAllOpenDocumentContentSame = panel.includeAllOpenDocumentContent.isSelected() == settings.includeAllOpenDocumentContent;
            return !(includeCurrentDocumentContentSame && includeCurrentSelectedContentSame && includeAllOpenDocumentContentSame);
        }

        public void apply(ChatSettingsState.ContextSettings settings) {
            settings.includeCurrentDocumentContent = panel.includeCurrentDocumentContent.isSelected();
            settings.includeCurrentSelectedContent = panel.includeCurrentSelectedContent.isSelected();
            settings.includeAllOpenDocumentContent = panel.includeAllOpenDocumentContent.isSelected();
        }

        public void reset(ChatSettingsState.ContextSettings settings) {
            panel.includeCurrentDocumentContent.setSelected(settings.includeCurrentDocumentContent);
            panel.includeCurrentSelectedContent.setSelected(settings.includeCurrentSelectedContent);
            panel.includeAllOpenDocumentContent.setSelected(settings.includeAllOpenDocumentContent);
        }
    }
}
