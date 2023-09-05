package io.github.folkly.guppi.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBTextField;

public class ChatSettingsPanel extends SettingsForm {
    public static String title = "Chat";

    public final JBTextField primer;
    public JBCheckBox includeCurrentDocumentContent;
    public JBCheckBox includeCurrentSelectedContent;
    public JBCheckBox includeAllOpenDocumentContent;

    public final ChatSettingsPresenter presenter;

    public ChatSettingsPanel() {
        super();

        primer = new JBTextField();
        addLabeledComponent("Primer ", primer);

        includeCurrentDocumentContent = new JBCheckBox();
        addLabeledComponent("Include current document content", includeCurrentDocumentContent);

        includeCurrentSelectedContent = new JBCheckBox();
        addLabeledComponent("Include current selected content", includeCurrentSelectedContent);

        includeAllOpenDocumentContent = new JBCheckBox();
        addLabeledComponent("Include all open document content", includeAllOpenDocumentContent);

        fill();

        presenter = new ChatSettingsPresenter(this);
    }
}
