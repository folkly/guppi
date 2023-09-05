package io.github.folkly.guppi.common.util;

import java.awt.*;
import java.util.Optional;

public class DimensionWrapper extends Dimension {
    public DimensionWrapper(int width, int height) {
        super(width, height);
    }

    public DimensionWrapper setWidth(int width) {
        return new DimensionWrapper(width, height);
    }

    public DimensionWrapper setHeight(int height) {
        return new DimensionWrapper(width, height);
    }

    public DimensionWrapper add(int thickness) {
        return new DimensionWrapper(width + 2 * thickness, height + 2 * thickness);
    }

    public DimensionWrapper add(Dimension right) {
        return new DimensionWrapper(width + right.width, height + right.height);
    }

    public DimensionWrapper addWidth(int widthToAdd) {
        return new DimensionWrapper(width + widthToAdd, height);
    }

    public DimensionWrapper addHeight(int heightToAdd) {
        return new DimensionWrapper(width, height + heightToAdd);
    }

    public DimensionWrapper clamp(Integer nullableMinWidth, Integer nullableMaxWidth, Integer nullableMinHeight, Integer nullableMaxHeight) {
        int minWidth = Optional.ofNullable(nullableMinWidth).orElse(0);
        int maxWidth = Optional.ofNullable(nullableMaxWidth).orElse(Integer.MAX_VALUE);
        int minHeight = Optional.ofNullable(nullableMinHeight).orElse(0);
        int maxHeight = Optional.ofNullable(nullableMaxHeight).orElse(Integer.MAX_VALUE);
        return clamp(minWidth, maxWidth, minHeight, maxHeight);
    }

    public DimensionWrapper clamp(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        int clampedWidth = Math.min(Math.max(width, minWidth), maxWidth);
        int clampedHeight = Math.min(Math.max(height, minHeight), maxHeight);
        return new DimensionWrapper(clampedWidth, clampedHeight);
    }

    public DimensionWrapper clampWidth(Integer nullableMinWidth, Integer nullableMaxWidth) {
        int minWidth = Optional.ofNullable(nullableMinWidth).orElse(0);
        int maxWidth = Optional.ofNullable(nullableMaxWidth).orElse(Integer.MAX_VALUE);
        return clampWidth(minWidth, maxWidth);
    }

    public DimensionWrapper clampWidth(int minWidth, int maxWidth) {
        int clampedWidth = Math.min(Math.max(width, minWidth), maxWidth);
        return new DimensionWrapper(clampedWidth, height);
    }

    public DimensionWrapper clampHeight(Integer nullableMinHeight, Integer nullableMaxHeight) {
        int minHeight = Optional.ofNullable(nullableMinHeight).orElse(0);
        int maxHeight = Optional.ofNullable(nullableMaxHeight).orElse(Integer.MAX_VALUE);
        return clampHeight(minHeight, maxHeight);
    }

    public DimensionWrapper clampHeight(int minHeight, int maxHeight) {
        int clampedHeight = Math.min(Math.max(height, minHeight), maxHeight);
        return new DimensionWrapper(width, clampedHeight);
    }

    public DimensionWrapper clampMin(Integer nullableMinWidth, Integer nullableMinHeight) {
        int minWidth = Optional.ofNullable(nullableMinWidth).orElse(0);
        int minHeight = Optional.ofNullable(nullableMinHeight).orElse(0);
        return clampMin(minWidth, minHeight);
    }

    public DimensionWrapper clampMin(int minWidth, int minHeight) {
        int clampedWidth = Math.max(width, minWidth);
        int clampedHeight = Math.max(height, minHeight);
        return new DimensionWrapper(clampedWidth, clampedHeight);
    }

    public DimensionWrapper clampMin(Dimension size) {
        if (size == null) {
            return this;
        } else {
            return clampMin(size.width, size.height);
        }
    }

    public DimensionWrapper clampMax(Integer nullableMaxWidth, Integer nullableMaxHeight) {
        int maxWidth = Optional.ofNullable(nullableMaxWidth).orElse(Integer.MAX_VALUE);
        int maxHeight = Optional.ofNullable(nullableMaxHeight).orElse(Integer.MAX_VALUE);
        return clampMax(maxWidth, maxHeight);
    }

    public DimensionWrapper clampMax(int maxWidth, int maxHeight) {
        int clampedWidth = Math.min(width, maxWidth);
        int clampedHeight = Math.min(height, maxHeight);
        return new DimensionWrapper(clampedWidth, clampedHeight);
    }

    public DimensionWrapper clampMax(Dimension size) {
        if (size == null) {
            return this;
        } else {
            return clampMax(size.width, size.height);
        }
    }

    public DimensionWrapper scale(double factor) {
        return new DimensionWrapper((int) (factor * width), (int) (factor * height));
    }

    public DimensionWrapper scaleWidth(double factor) {
        return new DimensionWrapper((int) (factor * width), height);
    }

    public DimensionWrapper scaleHeight(double factor) {
        return new DimensionWrapper(width, (int) (factor * height));
    }

    public Dimension dim() {
        return new Dimension(width, height);
    }

    public static DimensionWrapper from(Dimension dim) {
        return new DimensionWrapper(dim.width, dim.height);
    }
}
