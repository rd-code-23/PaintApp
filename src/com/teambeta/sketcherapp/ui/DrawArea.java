package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.GeneratorFunctions;
import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.LinkedList;

import static java.awt.Color.black;

/**
 * Class for drawable canvasBufferedImage.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private static final int TRANSPARENCY_CHECKER_BOARD_SIZE = 35;
    private MainUI mainUI;
    private BufferedImage canvasBufferedImage;
    private static BufferedImage layersBelowSelectedLayer;
    private static BufferedImage layersAboveSelectedLayer;
    private LinkedList<ImageLayer> drawingLayers;
    private ImageLayer currentlySelectedLayer;
    private static BufferedImage previewBufferedImage;
    private Graphics2D graphics;
    private Color backgroundColor;
    private boolean isCanvasAltered = false;
    private static final double RED_LUMA_COEFFICIENT = 0.2126;
    private static final double GREEN_LUMA_COEFFICIENT = 0.7152;
    private static final double BLUE_LUMA_COEFFICIENT = 0.0722;
    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    private BufferedImage checkerboardImage;

    /**
     * Constructor. Set actions upon mouse press events.
     */
    public DrawArea(MainUI mainUI) {
        backgroundColor = Color.WHITE;
        setBackground(backgroundColor);
        setDoubleBuffered(false);
        this.mainUI = mainUI;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (currentlySelectedLayer.isVisible()) {
                    MainUI.getSelectedDrawingTool().onClick(canvasBufferedImage, e, drawingLayers);
                    isCanvasAltered = true;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (currentlySelectedLayer.isVisible()) {
                    MainUI.getSelectedDrawingTool().onPress(canvasBufferedImage, e, drawingLayers);
                    isCanvasAltered = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (currentlySelectedLayer.isVisible()) {
                    MainUI.getSelectedDrawingTool().onRelease(canvasBufferedImage, e, drawingLayers);
                    isCanvasAltered = true;
                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (currentlySelectedLayer.isVisible()) {
                    MainUI.getSelectedDrawingTool().onDrag(canvasBufferedImage, e, drawingLayers);
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentlySelectedLayer.isVisible()) {
                    MainUI.getSelectedDrawingTool().onMove(canvasBufferedImage, e, drawingLayers);
                    isCanvasAltered = true;
                    repaint();
                }
            }
        });
        drawingLayers = new LinkedList<>();
    }

    /**
     * Clears buffer image.
     *
     * @param bufferedImage canvasBufferedImage to clear.
     */
    public static void clearBufferImageToTransparent(BufferedImage bufferedImage) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setColor(transparentColor);
        graphics.setBackground(transparentColor);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /**
     * Redraws the layers onto the canvas.
     */
    public void redrawLayers() {
        refreshBufferedImages();
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
    }

    /**
     * Draws the provided layers onto the provided canvasBufferedImage.
     *
     * @param layers the layers to draw
     * @param canvas the canvasBufferedImage to draw to
     */
    public static void drawLayersOntoCanvas(LinkedList<ImageLayer> layers, BufferedImage canvas) {
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        //clear the canvasBufferedImage.
        clearBufferImageToTransparent(canvas);
        //draw the layers in order
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);

        canvasGraphics.drawImage(layersBelowSelectedLayer, 0, 0, null);
        for (ImageLayer layer : layers) {
            if (layer != null) {
                if (layer.isSelected() && layer.isVisible()) {
                    canvasGraphics.drawImage(layer.getBufferedImage(), 0, 0, null);
                    break;
                }
            }
        }
        canvasGraphics.drawImage(layersAboveSelectedLayer, 0, 0, null);
    }

    /**
     * Defines how the DrawArea component is painted.
     *
     * @param canvasGraphics graphics for canvasBufferedImage
     */
    protected void paintComponent(Graphics canvasGraphics) {
        if (canvasBufferedImage == null) {
            firstTimeInit();
        }
        //draw the checkerboard pattern to represent transparency.
        canvasGraphics.drawImage(checkerboardImage, 0, 0, null);
        //draw the canvas image
        canvasGraphics.drawImage(canvasBufferedImage, 0, 0, null);
    }

    /**
     * Allows access to the ImageLayer the user has currently selected.
     *
     * @return The ImageLayer the user has currently selected.
     */
    public ImageLayer getCurrentlySelectedLayer() {
        return currentlySelectedLayer;
    }

    /**
     * Creates the BufferedImages and other objects that the DrawArea needs to function.
     */
    private void firstTimeInit() {
        // create a canvasBufferedImage to draw on
        canvasBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        previewBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        layersBelowSelectedLayer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        layersAboveSelectedLayer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        checkerboardImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        fillCheckerPattern(checkerboardImage,
                this.getWidth() / TRANSPARENCY_CHECKER_BOARD_SIZE,
                this.getHeight() / TRANSPARENCY_CHECKER_BOARD_SIZE,
                Color.WHITE, Color.LIGHT_GRAY
        );
        graphics = (Graphics2D) canvasBufferedImage.getGraphics();
        // enable antialiasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (drawingLayers.isEmpty()) {
            drawingLayers.add(new ImageLayer(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB),
                    null, 0));
            currentlySelectedLayer = drawingLayers.get(0);
            currentlySelectedLayer.setSelected(true);
            LayersPanel layersPanel = mainUI.getLayersPanel();
            DefaultListModel<ImageLayer> listModel = layersPanel.getListModel();
            listModel.addElement(drawingLayers.get(0));
            layersPanel.getListOfLayers().setSelectedIndex(0);
        }
        // clear draw area
        clear();
        redrawLayers();
    }

    /**
     * Blend the drawArea layers above and below the selected layer into two BufferedImages.
     */
    private void refreshBufferedImages() {
        Graphics2D layersBelowSelectedLayerGraphics = (Graphics2D) layersBelowSelectedLayer.getGraphics();
        Graphics2D layersAboveSelectedLayerGraphics = (Graphics2D) layersAboveSelectedLayer.getGraphics();
        //clear the BufferedImages.
        clearBufferImageToTransparent(layersBelowSelectedLayer);
        clearBufferImageToTransparent(layersAboveSelectedLayer);
        //blend the layers
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        layersBelowSelectedLayerGraphics.setComposite(alphaComposite);
        layersAboveSelectedLayerGraphics.setComposite(alphaComposite);
        int indexOfSelectedLayer = drawingLayers.indexOf(currentlySelectedLayer);
        for (int i = indexOfSelectedLayer - 1; i >= 0; i--) {
            ImageLayer layer = drawingLayers.get(i);
            if (layer.isVisible()) {
                layersAboveSelectedLayerGraphics.drawImage(layer.getBufferedImage(), 0, 0, null);
            }
        }
        for (int i = drawingLayers.size() - 1; i > indexOfSelectedLayer; i--) {
            ImageLayer layer = drawingLayers.get(i);
            if (layer.isVisible()) {
                layersBelowSelectedLayerGraphics.drawImage(layer.getBufferedImage(), 0, 0, null);
            }
        }
    }

    /**
     * Fill in a black and white checkerboard pattern to the layer.
     *
     * @param layer            The BufferedImage layer to write on
     * @param horizontal_count The amount of squares horizontally
     * @param vertical_count   The amount of squares vertically
     * @param color1           The color of the odd tiles.
     * @param color2           The color of the even tiles.
     */
    private void fillCheckerPattern(BufferedImage layer, int horizontal_count, int vertical_count,
                                    Color color1, Color color2) {
        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        Color old_color = layerGraphics.getColor();

        int square_width = layer.getWidth() / horizontal_count;
        int square_height = layer.getHeight() / vertical_count;
        int row = 0;
        boolean isBlack;

        for (int y = 0; y < layer.getHeight(); y += square_height) {
            if (y >= layer.getHeight()) y = layer.getHeight() - 1;
            // Alternate start colour for each row
            if (row % 2 == 0) {
                isBlack = true;
                layerGraphics.setColor(color1);
            } else {
                isBlack = false;
                layerGraphics.setColor(color2);
            }

            for (int x = 0; x < layer.getWidth(); x += square_width) {
                if (x >= layer.getWidth()) x = layer.getWidth() - 1;

                // TODO: The bottom and right edges aren't drawing properly due to integer division data loss
                // Maybe just draw the bottom and right edge squares up to their respective sides
                layerGraphics.fillRect(x, y, square_width, square_height);

                if (isBlack) {
                    layerGraphics.setColor(color2);
                } else {
                    layerGraphics.setColor(color1);
                }
                isBlack = !isBlack;
            }
            ++row;
        }
        layerGraphics.setColor(old_color);
    }

    /**
     * Clears written elements on canvasBufferedImage.
     */
    public void clear() {
        if (currentlySelectedLayer != null) {
            Graphics2D layerGraphics = (Graphics2D) currentlySelectedLayer.getBufferedImage().getGraphics();
            clearBufferImageToTransparent(currentlySelectedLayer.getBufferedImage());
            graphics.setPaint(black);
            layerGraphics.setPaint(black);
            drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        }
        isCanvasAltered = false;
        repaint();
    }

    /**
     * Set the graphics painter color.
     *
     * @param color the color to set the graphics painter to.
     */
    public void setColor(Color color) {
        graphics.setPaint(color);
    }

    /**
     * Get the background color of the canvasBufferedImage area.
     *
     * @return The color of the background area
     */
    @Override
    public Color getBackground() {
        return backgroundColor;
    }

    /**
     * Get the preview layer.
     *
     * @return preview layer.
     */
    public static BufferedImage getPreviewBufferedImage() {
        return previewBufferedImage;
    }

    /**
     * Get canvasBufferedImage.
     *
     * @return canvasBufferedImage.
     */
    public BufferedImage getCanvasBufferedImage() {
        return canvasBufferedImage;
    }

    /**
     * Set class specified canvasBufferedImage.
     *
     * @param canvasBufferedImage to set class to.
     */
    public void setCanvasBufferedImage(BufferedImage canvasBufferedImage) {
        this.canvasBufferedImage = canvasBufferedImage;
    }

    /**
     * lets user import an image
     *
     * @param image The BufferedImage to draw on the layer.
     */
    public void setImportedImage(BufferedImage image) {
        Graphics2D selectedLayerGraphics = (Graphics2D) currentlySelectedLayer.getBufferedImage().getGraphics();
        selectedLayerGraphics.drawImage(image, 0, 0, this);
        graphics.drawImage(image, 0, 0, this);
        //graphics.finalize();
        isCanvasAltered = false;
        redrawLayers();
        repaint();
    }

    /**
     * checks to see if the user has altered the canvasBufferedImage
     *
     * @return return if the canvas has been altered or not.
     */
    public boolean isCanvasAltered() {
        return isCanvasAltered;
    }

    /**
     * sets whether the canvasBufferedImage has been altered
     *
     * @param canvasAltered Set a boolean that represents if the canvas BufferedImage has been altered.
     */
    public void setCanvasAltered(boolean canvasAltered) {
        isCanvasAltered = canvasAltered;
    }

    /**
     * Redraw all canvasBufferedImage coordinates to the average of the coordinate's RGB values.
     */
    public void redrawToGreyscale() {
        // convert selected imageLayer to greyscale
        BufferedImage currentlySelectedLayerBufferedImage = currentlySelectedLayer.getBufferedImage();
        makeBufferedImageGrayscale(currentlySelectedLayerBufferedImage);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    private void makeBufferedImageGrayscale(BufferedImage layer) {
        Color color_at_point;
        int lumaValue;
        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {
                color_at_point = new Color(layer.getRGB(x, y), true);
                if (color_at_point.getAlpha() != 0) {
                    lumaValue = (int) (
                            RED_LUMA_COEFFICIENT * color_at_point.getRed()
                                    + GREEN_LUMA_COEFFICIENT * color_at_point.getGreen()
                                    + BLUE_LUMA_COEFFICIENT * color_at_point.getBlue()
                    );
                    if (lumaValue > 255) {
                        lumaValue = 255;
                    } else if (lumaValue < 0) {
                        lumaValue = 0;
                    }
                    layer.setRGB(x, y, new Color(lumaValue, lumaValue, lumaValue, color_at_point.getAlpha()).getRGB());
                }
            }
        }
    }

    /**
     * Draw random colourful noise on the selectedLayer.
     */
    public void colouredNoiseGenerator() {
        BufferedImage currentlySelectedLayerBufferedImage = currentlySelectedLayer.getBufferedImage();
        fillWithColouredNoise(currentlySelectedLayerBufferedImage);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    private void fillWithColouredNoise(BufferedImage layer) {
        Color color_at_point;
        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {
                color_at_point = new Color(
                        GeneratorFunctions.randomInt(0, 255),
                        GeneratorFunctions.randomInt(0, 255),
                        GeneratorFunctions.randomInt(0, 255)
                );
                layer.setRGB(x, y, color_at_point.getRGB());
            }
        }
    }

    /**
     * Invert the colours of the current layer.
     */
    public void drawInvertCurrentLayerColours() {
        invertLayerColours(currentlySelectedLayer.getBufferedImage());
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    /**
     * Invert layer colours.
     *
     * @param layer the layer to transform
     */
    private void invertLayerColours(BufferedImage layer) {
        Color color_at_point;
        final int RGBMAXVAL = 255;
        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {
                color_at_point = new Color(layer.getRGB(x, y), true);
                int alphaPreserve = color_at_point.getAlpha();
                color_at_point = new Color(RGBMAXVAL - color_at_point.getRed(),
                        RGBMAXVAL - color_at_point.getGreen(),
                        RGBMAXVAL - color_at_point.getBlue(),
                        alphaPreserve);
                layer.setRGB(x, y, color_at_point.getRGB());
            }
        }
    }

    /**
     * Fill in a black and white checkerboard pattern to the layer.
     *
     * @param layer            The BufferedImage layer to write on
     * @param horizontal_count The amount of squares horizontally
     * @param vertical_count   The amount of squares vertically
     */
    private void fillCheckerPattern(BufferedImage layer, int horizontal_count, int vertical_count) {
        fillCheckerPattern(layer, horizontal_count, vertical_count, Color.BLACK, Color.WHITE);
    }

    /**
     * Draw a black and white checkerboard pattern to all layers.
     *
     * @param horizontalCount The amount of squares horizontally
     * @param verticalCount   The amount of squares vertically
     */
    public void drawCheckerPattern(int horizontalCount, int verticalCount) {
        BufferedImage currentlySelectedLayerBufferedImage = currentlySelectedLayer.getBufferedImage();
        fillCheckerPattern(currentlySelectedLayerBufferedImage, horizontalCount, verticalCount);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    public LinkedList<ImageLayer> getDrawingLayers() {
        return drawingLayers;
    }

    public void setCurrentlySelectedLayer(ImageLayer currentlySelectedLayer) {
        this.currentlySelectedLayer.setSelected(false);
        this.currentlySelectedLayer = currentlySelectedLayer;
        this.currentlySelectedLayer.setSelected(true);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
    }

    /**
     * Use the RescaleOp class to transform an image's brightness and/or contrast
     * This assumes that the alpha layer remain as-is
     *
     * @param scaleFactor factor to adjust BufferedImage contrast
     * @param offset      offset to adjust BufferedImage brightness
     * @param hints       the RenderingHints to use
     */
    public void rescaleOperation(float scaleFactor, float offset, RenderingHints hints) {
        float scaleFactorArray[] = {scaleFactor, scaleFactor, scaleFactor, 1f};
        float offsetArray[] = {offset, offset, offset, 0f};
        rescaleOperation(scaleFactorArray, offsetArray, hints);
    }

    /**
     * Use the RescaleOp class to transform an image's brightness and/or contrast
     * Argument arrays are 4 floats-wide {RED, GREEN, BLUE, ALPHA}
     *
     * @param scaleFactor scaleFactor array to adjust BufferedImage contrast
     * @param offset      offset array to adjust BufferedImage brightness
     * @param hints       the RenderingHints to use
     */
    private void rescaleOperation(float[] scaleFactor, float[] offset, RenderingHints hints) {
        RescaleOp transformationOperation = new RescaleOp(scaleFactor, offset, hints);
        transformationOperation.filter(this.currentlySelectedLayer.getBufferedImage(),
                this.currentlySelectedLayer.getBufferedImage());
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    /**
     * Scale the current layer's transparency by a factor
     * (0.5f means half the value, 1.0 means maintain current alpha, 2.0 means double the alpha value.)
     *
     * @param transparencyFactor the factor to adjust the transparency
     */
    public void scaleTransparency(float transparencyFactor) {
        if (transparencyFactor < 0.0f) {
            transparencyFactor = 0.0f;
        }

        float scaleFactorArray[] = {1f, 1f, 1f, transparencyFactor};
        float offsetArray[] = {0f, 0f, 0f, 0f};
        rescaleOperation(scaleFactorArray, offsetArray, null);
    }

    /**
     * Multiply the current layer's hue by some float factor.
     * (0.0 effectively means set the hue to zero, 0.5 means half hue, 2.0 means double hue)
     *
     * @param hueFactor the factor to multiply current hue levels
     */
    public void drawLayerHueScaling(float hueFactor) {
        transformLayerHSB(getCurrentlySelectedLayer().getBufferedImage(),
                hueFactor, 1f, 1f);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    /**
     * Multiply the current layer's saturation by some float factor.
     * (0.0 effectively means set the saturation to zero, 0.5 means half saturation, 2.0 means double saturation)
     *
     * @param saturationFactor the factor to multiply current saturation levels
     */
    public void drawLayerSaturationScaling(float saturationFactor) {
        transformLayerHSB(getCurrentlySelectedLayer().getBufferedImage(),
                1f, saturationFactor, 1f);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    /**
     * Multiply the current layer's brightness by some float factor.
     * (0.0 effectively means set the brightness to zero, 0.5 means half brightness, 2.0 means double brightness)
     * <p>
     * NOTE: This may act differently than rescaleOperation.
     *
     * @param brightnessFactor the factor to multiply current brightness levels
     */
    public void drawLayerBrightnessScaling(float brightnessFactor) {
        transformLayerHSB(getCurrentlySelectedLayer().getBufferedImage(),
                1f, 1f, brightnessFactor);
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
        repaint();
    }

    /**
     * Multiply the current layer's HSB by some float factors.
     *
     * @param layer            the layer to transform
     * @param hueFactor        the factor to multiply current hue levels
     * @param saturationFactor the factor to multiply current saturation levels
     * @param brightnessFactor the factor to multiply current brightness levels
     */
    private void transformLayerHSB(BufferedImage layer, float hueFactor, float saturationFactor, float brightnessFactor) {

        hueFactor = Math.abs(hueFactor);
        saturationFactor = Math.abs(saturationFactor);
        brightnessFactor = Math.abs(brightnessFactor);

        Color originalPointColor;
        Color newPointColor;
        float[] hsbArray;
        int alphaPreserve;
        int newRBG;

        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {

                originalPointColor = new Color(layer.getRGB(x, y), true);
                if (originalPointColor.getAlpha() == 0) {
                    continue;
                }
                alphaPreserve = originalPointColor.getAlpha();

                hsbArray = Color.RGBtoHSB(originalPointColor.getRed(), originalPointColor.getGreen(),
                        originalPointColor.getBlue(), null);
                hsbArray[0] *= hueFactor;
                hsbArray[1] *= saturationFactor;
                hsbArray[2] *= brightnessFactor;

                // Bound HSB values
                for (int i = 0; i < 3; ++i) {
                    hsbArray[i] = Math.max(Math.min(hsbArray[i], 1.0f), 0.0f);
                }

                newRBG = Color.HSBtoRGB(hsbArray[0], hsbArray[1], hsbArray[2]);

                newPointColor = new Color(newRBG, false);

                layer.setRGB(x, y, new Color(newPointColor.getRed(), newPointColor.getGreen(),
                        newPointColor.getBlue(), alphaPreserve).getRGB());
            }
        }
    }
}