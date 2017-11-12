package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.GeneratorFunctions;
import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static java.awt.Color.black;

/**
 * Class for drawable canvasBufferedImage.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private MainUI mainUI;
    private BufferedImage canvasBufferedImage;
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
    public void refreshLayers() {
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
    }

    /**
     * Draws the provided layers onto the provided canvasBufferedImage.
     *
     * @param layers the layers to draw
     * @param canvas the canvasBufferedImage to draw to
     */
    private static void drawLayersOntoCanvas(BufferedImage[] layers, BufferedImage canvas) {
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        //TODO:change to get actual color
        //clear the canvasBufferedImage to its default color
        canvasGraphics.setColor(Color.WHITE);
        canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        clearBufferImageToTransparent(canvas);
        //draw the layers in order
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        for (BufferedImage layer : layers) {
            if (layer != null) {
                canvasGraphics.drawImage(layer, 0, 0, null);
            }
        }
    }

    public static void drawLayersOntoCanvas(LinkedList<ImageLayer> layers, BufferedImage canvas) {
        BufferedImage[] bufferedImages = new BufferedImage[layers.size()];
        for (int i = 0; i < bufferedImages.length; i++) {
            if (layers.get(i).isVisible()) {
                bufferedImages[i] = layers.get(i).getBufferedImage();
            }
        }
        drawLayersOntoCanvas(bufferedImages, canvas);
    }

    /**
     * Creates a canvasBufferedImage for drawable elements.
     *
     * @param canvasGraphics graphics for canvasBufferedImage
     */
    protected void paintComponent(Graphics canvasGraphics) {
        if (canvasBufferedImage == null) {
            firstTimeInit();
        }
        canvasGraphics.drawImage(canvasBufferedImage, 0, 0, null);
    }

    public ImageLayer getCurrentlySelectedLayer() {
        return currentlySelectedLayer;
    }

    private void firstTimeInit() {
        // create a canvasBufferedImage to draw on
        canvasBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        previewBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = (Graphics2D) canvasBufferedImage.getGraphics();
        // enable antialiasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (drawingLayers.isEmpty()) {
            drawingLayers.add(new ImageLayer(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB)));
            drawingLayers.add(new ImageLayer(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB)));
            currentlySelectedLayer = drawingLayers.get(0);
            currentlySelectedLayer.setSelected(true);
            LayersPanel layersPanel = mainUI.getLayersPanel();
            DefaultListModel<ImageLayer> listModel = layersPanel.getListModel();
            listModel.addElement(drawingLayers.get(0));
            listModel.addElement(drawingLayers.get(1));
            layersPanel.getListOfLayers().setSelectedIndex(0);
        }
        // clear draw area
        clear();
        drawLayersOntoCanvas(drawingLayers, canvasBufferedImage);
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
        // Redraw all of the layers to greyscale
//        for (ImageLayer layer : drawingLayers) {
//            makeBufferedImageGrayscale(layer.getBufferedImage());
//        }

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
                if (color_at_point.getRGB() != -1) {
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
//        for (ImageLayer layer : drawingLayers) {
//            fillWithColouredNoise(layer.getBufferedImage());
//        }

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
     * Fill in a black and white checkerboard pattern to the layer.
     *
     * @param layer            The BufferedImage layer to write on
     * @param horizontal_count The amount of squares horizontally
     * @param vertical_count   The amount of squares vertically
     */
    private void fillCheckerPattern(BufferedImage layer, int horizontal_count, int vertical_count) {
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
                layerGraphics.setColor(Color.BLACK);
            } else {
                isBlack = false;
                layerGraphics.setColor(Color.WHITE);
            }

            for (int x = 0; x < layer.getWidth(); x += square_width) {
                if (x >= layer.getWidth()) x = layer.getWidth() - 1;

                // TODO: The bottom and right edges aren't drawing properly due to integer division data loss
                // Maybe just draw the bottom and right edge squares up to their respective sides
                layerGraphics.fillRect(x, y, square_width, square_height);

                if (isBlack) {
                    layerGraphics.setColor(Color.WHITE);
                } else {
                    layerGraphics.setColor(Color.BLACK);
                }
                isBlack = !isBlack;
            }
            ++row;
        }
        layerGraphics.setColor(old_color);
    }

    /**
     * Draw a black and white checkerboard pattern to all layers.
     *
     * @param horizontalCount The amount of squares horizontally
     * @param verticalCount   The amount of squares vertically
     */
    public void drawCheckerPattern(int horizontalCount, int verticalCount) {
//        for (ImageLayer layer : drawingLayers) {
//            fillCheckerPattern(layer.getBufferedImage(), horizontalCount, verticalCount);
//        }
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
}