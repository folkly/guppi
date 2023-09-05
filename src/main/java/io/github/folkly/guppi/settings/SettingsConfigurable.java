package io.github.folkly.guppi.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides Presenter functionality for application settings.
 */
public class SettingsConfigurable implements Configurable {

    private SettingsPanel settingsPanel;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Guppi Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsPanel = new SettingsPanel();
        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        if (settingsPanel == null) {
            return false;
        }
        SettingsState settings = SettingsState.getInstance();
        return settingsPanel.chatSettingsPanel.presenter.isModified(settings.chatSettingsState) ||
                settingsPanel.openAiSettingsPanel.presenter.isModified(settings);
    }

    @Override
    public void apply() {
        SettingsState settings = SettingsState.getInstance();
        settingsPanel.chatSettingsPanel.presenter.apply(settings.chatSettingsState);
        settingsPanel.openAiSettingsPanel.presenter.apply(settings);
    }

    @Override
    public void reset() {
        SettingsState settings = SettingsState.getInstance();
        settingsPanel.chatSettingsPanel.presenter.reset(settings.chatSettingsState);
        settingsPanel.openAiSettingsPanel.presenter.reset(settings);
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
    }

}
