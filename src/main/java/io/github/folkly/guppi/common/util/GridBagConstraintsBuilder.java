package io.github.folkly.guppi.common.util;

import com.intellij.util.ui.GridBag;

import java.awt.*;
import java.util.Optional;

public class GridBagConstraintsBuilder {
    protected Integer gridx;
    protected Integer gridy;
    protected Integer gridwidth;
    protected Integer gridheight;
    protected Double weightx;
    protected Double weighty;
    protected Integer anchor;
    protected Integer fill;

    public static GridBagConstraintsBuilder builder() {
        return new GridBagConstraintsBuilder();
    }

    public GridBagConstraints build() {
        GridBagConstraints c = new GridBagConstraints();
        Optional.ofNullable(gridx).ifPresent(value -> { c.gridx = value; });
        Optional.ofNullable(gridy).ifPresent(value -> { c.gridy = value; });
        Optional.ofNullable(gridwidth).ifPresent(value -> { c.gridwidth = value; });
        Optional.ofNullable(gridheight).ifPresent(value -> { c.gridheight = value; });
        Optional.ofNullable(weightx).ifPresent(value -> { c.weightx = value; });
        Optional.ofNullable(weighty).ifPresent(value -> { c.weighty = value; });
        Optional.ofNullable(anchor).ifPresent(value -> { c.anchor = value; });
        Optional.ofNullable(fill).ifPresent(value -> { c.fill = value; });
        return c;
    }

    public GridBagConstraintsBuilder gridx(int value) {
        gridx = value;
        return this;
    }

    public GridBagConstraintsBuilder gridy(int value) {
        gridy = value;
        return this;
    }

    public GridBagConstraintsBuilder gridwidth(int value) {
        gridwidth = value;
        return this;
    }

    public GridBagConstraintsBuilder gridheight(int value) {
        gridheight = value;
        return this;
    }

    public GridBagConstraintsBuilder weightx(double value) {
        weightx = value;
        return this;
    }

    public GridBagConstraintsBuilder weighty(double value) {
        weighty = value;
        return this;
    }

    public GridBagConstraintsBuilder anchor(int value) {
        anchor = value;
        return this;
    }

    public GridBagConstraintsBuilder fill(int value) {
        fill = value;
        return this;
    }

    protected GridBagConstraintsBuilder() {
        gridx = null;
        gridy = null;
        gridwidth = null;
        gridheight = null;
        weightx = null;
        weighty = null;
        anchor = null;
        fill = null;
    }
}
