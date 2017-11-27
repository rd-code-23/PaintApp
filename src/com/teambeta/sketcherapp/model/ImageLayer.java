package com.teambeta.sketcherapp.model;

import com.sun.org.apache.bcel.internal.generic.DUP;

import java.awt.image.BufferedImage;

/**
 * The Layer class represents a drawing layer for our painting app.
 */
public class ImageLayer {
    private boolean isVisible;
    private BufferedImage bufferedImage;
    private boolean isSelected;
    private String name;
    private String originalLayerName;
    private int duplicationCount;
    private static int layerNumber = 1;
    private final static String HIDDEN = "  [Hidden]";
    private final static String DUPLICATE = "Duplicate";
    private final static String LEFTBRACKET = "(";
    private final static String RIGHTBRACKET = ")";

    /**
     * Constructor.
     *
     * @param bufferedImage for the image layer.
     * @param originalLayerName the name of the original layer if this is a duplicate. Pass null for new layer.
     */
    public ImageLayer(BufferedImage bufferedImage, String originalLayerName, int duplicationCount) {
        this.bufferedImage = bufferedImage;
        isVisible = true;
        isSelected = false;
        if (originalLayerName == null && duplicationCount == 0) {
            this.name = "  Layer " + layerNumber;
            this.originalLayerName = this.name;
            this.duplicationCount = 0;
        } else {
            this.originalLayerName = originalLayerName;
            this.name = originalLayerName + " " + LEFTBRACKET + DUPLICATE + " " + duplicationCount + RIGHTBRACKET;
            this.duplicationCount = duplicationCount + 1;
        }
        layerNumber++;
    }

    /**
     * Resets the layerNumber to 1.
     */
    public static void resetLayerNumber() {
        layerNumber = 1;
    }

    /**
     * Get the buffered image.
     *
     * @return bufferedImage
     */
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    /**
     * Checks if layer is visible.
     *
     * @return true if layer if visible, false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Set visibility of the layer.
     *
     * @param visible sets visibility of layer.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Specifies if layer is selected.
     *
     * @param selected sets layer to selected.
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    /**
     * Checks if layer is selected.
     *
     * @return true if layer is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Get the name of the image layer.
     *
     * @return name of layer.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the image layer.
     *
     * @param name for the layer.
     */
    public void setName(String name) {
        this.name = "  " + name;
    }

    public void setDuplicationCount(int duplicationCount) {
        this.duplicationCount = duplicationCount;
    }

    public int getDuplicationCount() {
        return duplicationCount;
    }

    public void setOriginalLayerName(String name) {
        originalLayerName = "  " + name;
    }

    public String getOriginalLayerName() {
        if (originalLayerName != null) {
            return originalLayerName;
        }
        return "";
    }

    @Override
    public String toString() {
        if (!isVisible) {
            return HIDDEN + " " + name;
        } else {
            return name;
        }
    }
}