package io.github.folkly.guppi.settings;

import com.intellij.icons.AllIcons;
import io.github.folkly.guppi.integration.openai.OpenAi;
import io.github.folkly.openai.api.Api;
import io.github.folkly.openai.model.v1.models.get.Models;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpenAiSettingsPresenter {
    public final OpenAiSettingsPanel panel;
    protected final ChatModelPresenter chatModelPresenter;

    public OpenAiSettingsPresenter(OpenAiSettingsPanel panel) {
        this.panel = panel;
        chatModelPresenter = new ChatModelPresenter(panel);

        panel.testApiKeyButton.addActionListener(e -> handleTestApiKeyButton());
    }

    public boolean isModified(SettingsState settings) {
        boolean chatModelSame = chatModelPresenter.isModified(settings);
        boolean apiKeySame = getApiKeyFromField().equals(settings.openAiSettingsState.apiKey);
        return !(chatModelSame && apiKeySame);
    }

    public void apply(SettingsState settings) {
        chatModelPresenter.apply(settings);
        settings.openAiSettingsState.apiKey = getApiKeyFromField();
    }

    public void reset(SettingsState settings) {
        chatModelPresenter.reset(settings);
        panel.apiKeyField.setText(settings.openAiSettingsState.apiKey);
    }

    protected void handleTestApiKeyButton() {
        Api api = OpenAi.getApi(getApiKeyFromField());
        try {
            Models models = api.v1().getModels();
            panel.apiKeyTestStatusLabel.setIcon(AllIcons.General.InspectionsOK);
            panel.apiKeyTestStatusLabel.setText("OK");
        } catch (IOException e) {
            panel.apiKeyTestStatusLabel.setIcon(AllIcons.General.Error);
            panel.apiKeyTestStatusLabel.setText(String.format("Failed: %s", e));
        }
    }

    protected String getApiKeyFromField() {
        return new String(panel.apiKeyField.getPassword());
    }

    protected static String getApiKeyFromState() {
        return getApiKeyFromState(SettingsState.getInstance());
    }

    protected static String getApiKeyFromState(SettingsState settingsState) {
        return settingsState.openAiSettingsState.apiKey;
    }

    public static class ChatModelPresenter {
        public final OpenAiSettingsPanel panel;

        protected static String CUSTOM_INPUT = "Custom Input";

        public ChatModelPresenter(OpenAiSettingsPanel openAiSettingsPanel) {
            panel = openAiSettingsPanel;

            updateChatModelOptions();

            panel.chatModelDropdown.addActionListener(e -> handleChatModelSelected());
        }

        public boolean isModified(SettingsState settings) {
            return getChatModel().equals(settings.openAiSettingsState.chatModel);
        }

        public void apply(SettingsState settings) {
            settings.openAiSettingsState.chatModel = getChatModel();
        }

        public void reset(SettingsState settings) {
            String model = settings.openAiSettingsState.chatModel;
            if (getChatModelsFromDropdown().contains(model)) {
                panel.chatModelDropdown.setSelectedItem(model);
                panel.chatModelField.setText("");
                panel.chatModelField.setVisible(false);
            } else {
                panel.chatModelDropdown.setSelectedItem(CUSTOM_INPUT);
                panel.chatModelField.setText(model);
                panel.chatModelField.setVisible(true);
            }
        }

        protected void updateChatModelOptions() {
            DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) panel.chatModelDropdown.getModel();
            comboBoxModel.removeAllElements();
            comboBoxModel.addAll(getChatModelsFromApi());
            comboBoxModel.addElement(CUSTOM_INPUT);
        }

        public void handleChatModelSelected() {
            String selected = (String) Optional.ofNullable(panel.chatModelDropdown.getSelectedItem()).orElse("");
            if (selected.equals(CUSTOM_INPUT)) {
                panel.chatModelField.setVisible(true);
            } else {
                panel.chatModelField.setVisible(false);
            }
        }

        public String getChatModel() {
            String selected = (String) Optional.ofNullable(panel.chatModelDropdown.getSelectedItem()).orElse("");
            if (selected.equals(CUSTOM_INPUT)) {
                return panel.chatModelField.getText();
            } else {
                return selected;
            }
        }

        public List<String> getChatModelsFromDropdown() {
            List<String> models = new ArrayList<>();
            DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) panel.chatModelDropdown.getModel();
            for (int i = 0; i < comboBoxModel.getSize(); i++) {
                models.add(comboBoxModel.getElementAt(i));
            }
            return models;
        }

        public List<String> getChatModelsFromApi() {
            try {
                Models models = OpenAi.getApi().v1().getModels();
                return models.data.stream().map(m -> m.id).toList();
            } catch (IOException | NullPointerException e) {
                return List.of();
            }
        }
    }
}
