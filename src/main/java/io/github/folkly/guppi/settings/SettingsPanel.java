package io.github.folkly.guppi.settings;

import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;
import java.awt.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class SettingsPanel extends JBPanel<SettingsPanel> {

    public final JBTabbedPane tabbedPane;
    public final ChatSettingsPanel chatSettingsPanel;
    public final OpenAiSettingsPanel openAiSettingsPanel;

    public SettingsPanel() {
        setLayout(new GridBagLayout());

        tabbedPane = new JBTabbedPane();
        add(tabbedPane, getGridBagConstraints());

        chatSettingsPanel = new ChatSettingsPanel();
        tabbedPane.add(ChatSettingsPanel.title, chatSettingsPanel);

        openAiSettingsPanel = new OpenAiSettingsPanel();
        tabbedPane.add(OpenAiSettingsPanel.title, openAiSettingsPanel);
    }

    public static GridBagConstraints getGridBagConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        return constraints;
    }

}
