package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.GeneratorFunctions;
import com.teambeta.sketcherapp.model.ImageLayer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JComponent;

import static java.awt.Color.black;

/**
 * Class for drawable canvasBufferedImage.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private BufferedImage canvasBufferedImage;
    private BufferedImage[] layers;
    private LinkedList<ImageLayer> drawingLayers;
    private ImageLayer currentlySelectedLayer;
    private static BufferedImage previewBufferedImage;
    private Graphics2D graphics;
    private Color backgroundColor;
    private boolean isCanvasAltered = false;

    private static final double RED_LUMA_COEFFICIENT = 0.2126;
    private static final double GREEN_LUMA_COEFFICIENT = 0.7152;
    private static final double BLUE_LUMA_COEFFICIENT = 0.0722;

    /**
     * Constructor. Set actions upon mouse press events.
     */
    public DrawArea() {
        backgroundColor = Color.WHITE;
        setBackground(backgroundColor);
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainUI.getSelectedDrawingTool().onClick(canvasBufferedImage, layers, e, drawingLayers);
                isCanvasAltered = true;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                MainUI.getSelectedDrawingTool().onPress(canvasBufferedImage, layers, e, drawingLayers);
                isCanvasAltered = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainUI.getSelectedDrawingTool().onRelease(canvasBufferedImage, layers, e, drawingLayers);
                isCanvasAltered = true;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                MainUI.getSelectedDrawingTool().onDrag(canvasBufferedImage, drawingLayers, layers, e);
                repaint();
            }
        });

        layers = new BufferedImage[1];
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
        Color transparentColor = new Color(0x00FFFFFF, true);
        graphics.setColor(transparentColor);
        graphics.setBackground(transparentColor);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /**
     * Draws the provided layers onto the provided canvasBufferedImage.
     *
     * @param layers the layers to draw
     * @param canvas the canvasBufferedImage to draw to
     */
    public static void drawLayersOntoCanvas(BufferedImage[] layers, BufferedImage canvas) {
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        //TODO:change to get actual color
        //clear the canvasBufferedImage to its default color
        canvasGraphics.setColor(Color.WHITE);
        canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //draw the layers in order
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);

        for (BufferedImage layer : layers) {
            canvasGraphics.drawImage(layer, 0, 0, null);
        }
    }

    public static void drawLayersOntoCanvas(LinkedList<ImageLayer> layers, BufferedImage canvas) {
        BufferedImage[] bufferedImages = new BufferedImage[layers.size()];
        for (int i = 0; i < bufferedImages.length; i++) {
            bufferedImages[i] = layers.get(i).getBufferedImage();
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
            // create a canvasBufferedImage to draw on
            canvasBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < layers.length; i++) {
                layers[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
            previewBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = (Graphics2D) canvasBufferedImage.getGraphics();
            // enable antialiasing
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }
        if (drawingLayers.isEmpty()) {
            drawingLayers.add(new ImageLayer(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB)));
            currentlySelectedLayer = drawingLayers.get(0);
            currentlySelectedLayer.setSelected(true);
        }
        canvasGraphics.drawImage(canvasBufferedImage, 0, 0, null);
    }

    /**
     * Clears written elements on canvasBufferedImage.
     */
    public void clear() {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setPaint(Color.white);
        graphics.setPaint(Color.white);
        // draw white on entire draw area to clear
        graphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
        layer1Graphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
        graphics.setPaint(black);
        layer1Graphics.setPaint(black);

        if (currentlySelectedLayer != null) {
            Graphics2D currentlySelectedLayersGraphics =
                    (Graphics2D) currentlySelectedLayer.getBufferedImage().getGraphics();
            currentlySelectedLayersGraphics.setPaint(Color.white);
            currentlySelectedLayersGraphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
            currentlySelectedLayersGraphics.setPaint(black);
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
     * @param image
     */
    public void setImportedImage(BufferedImage image) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.drawImage(image, 0, 0, this);

        Graphics2D currentlySelectedLayersGraphics =
                (Graphics2D) currentlySelectedLayer.getBufferedImage().getGraphics();
        currentlySelectedLayersGraphics.drawImage(image, 0, 0, this);

        graphics.drawImage(image, 0, 0, this);
        graphics.finalize();
        isCanvasAltered = false;
        repaint();
    }

    /**
     * checks to see if the user has altered the canvasBufferedImage
     *
     * @return
     */
    public boolean isCanvasAltered() {
        return isCanvasAltered;
    }

    /**
     * sets whether the canvasBufferedImage has been altered
     *
     * @param canvasAltered
     */
    public void setCanvasAltered(boolean canvasAltered) {
        isCanvasAltered = canvasAltered;
    }

    /**
     * Redraw all canvasBufferedImage coordinates to the average of the coordinate's RGB values.
     */
    public void redrawToGreyscale() {
        // Redraw all of the layers to greyscale
        for (BufferedImage layer : layers) {
            makeBufferedImageGrayscale(layer);
        }

        // Redraw current imageLayer to greyscale
        BufferedImage currentlySelectedLayerBufferedImage = currentlySelectedLayer.getBufferedImage();
        makeBufferedImageGrayscale(currentlySelectedLayerBufferedImage);
        drawLayersOntoCanvas(layers, canvasBufferedImage);
        repaint();
    }

    private void makeBufferedImageGrayscale(BufferedImage layer) {
        Color color_at_point;
        int lumaValue;
        for (int x = 0; x < layer.getWidth(); ++x) {
            for (int y = 0; y < layer.getHeight(); ++y) {
                color_at_point = new Color(layer.getRGB(x, y));
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
                    layer.setRGB(x, y, new Color(lumaValue, lumaValue, lumaValue).getRGB());
                }
            }
        }
    }

    /**
     * Draw random colourful noise on the canvasBufferedImage.
     */
    public void colouredNoiseGenerator() {
        for (BufferedImage layer : layers) {
            fillWithColouredNoise(layer);
        }
        drawLayersOntoCanvas(layers, canvasBufferedImage);
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
     * @param layer The BufferedImage layer to write on
     * @param horizontal_count The amount of squares horizontally
     * @param vertical_count The amount of squares vertically
     */
    private void fillCheckerPattern(BufferedImage layer, int horizontal_count, int vertical_count) {

        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        Color old_color = layerGraphics.getColor();

        int square_width = layer.getWidth() / horizontal_count;
        int square_height = layer.getHeight() / vertical_count;
        int row = 1;
        boolean isBlack = false;

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

                // TODO: The bottom and right edges aren't drawing properly.
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
     * @param verticalCount The amount of squares vertically
     */
    public void drawCheckerPattern(int horizontalCount, int verticalCount) {
        for (BufferedImage layer : layers) {
            fillCheckerPattern(layer, horizontalCount, verticalCount);
        }
        drawLayersOntoCanvas(layers, canvasBufferedImage);
        repaint();
    }

}
