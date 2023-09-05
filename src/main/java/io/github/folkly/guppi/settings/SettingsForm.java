package io.github.folkly.guppi.settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import io.github.folkly.guppi.common.util.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;

public class SettingsForm extends JBPanel<SettingsForm> {

    int maxGridX = -1;
    int maxGridY = -1;

    public SettingsForm() {
        setLayout(new GridBagLayout());
    }

    public void addLabeledComponent(String label, JComponent component) {
        addLabeledComponent(label, component, maxGridY + 1);
    }

    public void addLabeledComponent(String label, JComponent component, int gridy) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.gridx = 0;
        labelConstraints.gridy = gridy;

        add(new JBLabel(label), labelConstraints);

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.anchor = GridBagConstraints.WEST;
        componentConstraints.gridx = 1;
        componentConstraints.gridy = gridy;

        add(component, componentConstraints);
    }

    @Override
    public Component add(Component comp) {
        add(comp, constraints().gridy(maxGridY + 1));
        return comp;
    }

    public void add(Component comp, GridBagConstraintsBuilder constraintsBuilder) {
        super.add(comp, constraintsBuilder.build());
    }

    public void add(Component comp, GridBagConstraints constraints) {
        super.add(comp, constraints);
        maxGridX = Math.max(maxGridX, constraints.gridx);
        maxGridY = Math.max(maxGridY, constraints.gridy);
    }

    protected void fill() {
        add(new JPanel(), constraints()
                .gridx(maxGridX + 1)
                .gridy(maxGridY + 1)
                .weightx(1)
                .weighty(1)
                .gridwidth(GridBagConstraints.REMAINDER)
                .gridheight(GridBagConstraints.REMAINDER)
                .build()
        );
    }

    public static GridBagConstraintsBuilder constraints() {
        return GridBagConstraintsBuilder.builder();
    }
}
