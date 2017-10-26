package com.teambeta.sketcherapp.model;

import java.awt.image.BufferedImage;

/**
 * The Layer class represents a drawing layer for our painting app.
 */
public class ImageLayer {
    private boolean isVisible;
    private BufferedImage bufferedImage;
    private boolean isSelected;

    public ImageLayer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        isVisible = false;
        isSelected = false;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
