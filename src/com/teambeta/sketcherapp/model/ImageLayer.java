package com.teambeta.sketcherapp.model;

import java.awt.image.BufferedImage;

/**
 * The Layer class represents a drawing layer for our painting app.
 */
public class ImageLayer {
    private boolean isVisible;
    private BufferedImage bufferedImage;

    public ImageLayer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        isVisible = false;
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
}
