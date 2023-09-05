package io.github.folkly.guppi.common.ui.geom;

import org.jetbrains.annotations.NotNull;

import javax.swing.border.LineBorder;
import java.awt.*;

public class MyRoundedLineBorder extends LineBorder {
    private int myArcSize = 1;

    public MyRoundedLineBorder(Color color) {
        super(color);
    }

    public MyRoundedLineBorder(Color color, int arcSize) {
        this(color, arcSize, 1);
    }

    public MyRoundedLineBorder(Color color, int arcSize, final int thickness) {
        super(color, thickness);
        myArcSize = arcSize;
    }

    public void setColor(@NotNull Color color) {
        lineColor = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        final Graphics2D g2 = (Graphics2D) g;

        final Color oldColor = g2.getColor();
        final Object oldAntialiasing = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        final Object oldRending = g2.getRenderingHint(RenderingHints.KEY_RENDERING);

        g2.setColor(lineColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for (int i = 0; i < thickness; i++) {
            g2.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, myArcSize, myArcSize);
        }

        g2.setColor(oldColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntialiasing);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, oldRending);
    }
}
