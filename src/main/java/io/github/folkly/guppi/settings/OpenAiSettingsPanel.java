package io.github.folkly.guppi.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;

public class OpenAiSettingsPanel extends SettingsForm {

    public static String title = "OpenAI";

    public final ComboBox<String> chatModelDropdown;
    public final JBTextField chatModelField;
    public final JBPasswordField apiKeyField;
    public final JButton testApiKeyButton;
    public JLabel apiKeyTestStatusLabel;

    public final OpenAiSettingsPresenter presenter;

    public OpenAiSettingsPanel() {

        chatModelDropdown = new ComboBox<>();
        addLabeledComponent("Chat Model ", chatModelDropdown);

        chatModelField = new JBTextField();
        add(chatModelField, constraints().gridx(2).gridy(1).build());

        apiKeyField = new JBPasswordField();
        addLabeledComponent("API Key ", apiKeyField, 2);

        testApiKeyButton = new JButton("Test API Key");
        add(testApiKeyButton, constraints().gridx(2).gridy(2).build());

        apiKeyTestStatusLabel = new JBLabel();
        add(apiKeyTestStatusLabel, constraints().gridx(3).gridy(2).build());

        fill();

        presenter = new OpenAiSettingsPresenter(this);
    }
}
