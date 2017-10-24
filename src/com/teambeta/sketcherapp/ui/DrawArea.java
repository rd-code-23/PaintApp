package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.GeneratorFunctions;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JComponent;

/**
 * Class for drawable canvas.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private BufferedImage canvas;
    private BufferedImage[] layers = new BufferedImage[1];
    private static BufferedImage previewLayer;
    private Graphics2D graphics;
    private Color backgroundColor;
    private boolean isCanvasAltered = false;

    private static final double RED_LUMA_COEFFICIENT = 0.2126;
    private static final double GREEN_LUMA_COEFFICIENT = 0.7152;
    private static final double BLUE_LUMA_COEFFICIENT  = 0.0722;

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
                MainUI.selectedDrawingTool.onClick(canvas, layers, e);
                isCanvasAltered = true;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                MainUI.selectedDrawingTool.onPress(canvas, layers, e);
                isCanvasAltered = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainUI.selectedDrawingTool.onRelease(canvas, layers, e);
                isCanvasAltered = true;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                MainUI.selectedDrawingTool.onDrag(canvas, layers, e);
                repaint();
            }
        });
    }

    /**
     * Clears buffer image.
     *
     * @param bufferedImage canvas to clear.
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
     * Draws the provided layers onto the provided canvas.
     *
     * @param layers the layers to draw
     * @param canvas the canvas to draw to
     */
    public static void drawLayersOntoCanvas(BufferedImage[] layers, BufferedImage canvas) {
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        //TODO:change to get actual color
        //clear the canvas to its default color
        canvasGraphics.setColor(Color.WHITE);
        canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //draw the layers in order
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);

        for (BufferedImage layer : layers) {
            canvasGraphics.drawImage(layer, 0, 0, null);
        }
    }

    /**
     * Creates a canvas for drawable elements.
     *
     * @param canvasGraphics graphics for canvas
     */
    protected void paintComponent(Graphics canvasGraphics) {
        if (canvas == null) {
            // create a canvas to draw on
            canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < layers.length; i++) {
                layers[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
            previewLayer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = (Graphics2D) canvas.getGraphics();
            // enable antialiasing
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }
        canvasGraphics.drawImage(canvas, 0, 0, null);
    }

    /**
     * Clears written elements on canvas.
     */
    public void clear() {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setPaint(Color.white);
        graphics.setPaint(Color.white);
        // draw white on entire draw area to clear
        graphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
        layer1Graphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
        graphics.setPaint(Color.black);
        layer1Graphics.setPaint(Color.black);
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
     * Get the background color of the canvas area.
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
    public static BufferedImage getPreviewLayer() {
        return previewLayer;
    }

    /**
     * Get canvas.
     *
     * @return canvas.
     */
    public BufferedImage getCanvas() {
        return canvas;
    }

    /**
     * Set class specified canvas.
     *
     * @param canvas to set class to.
     */
    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }

    /**
     * lets user import an image
     *
     * @param image
     */
    public void setImportedImage(BufferedImage image) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.drawImage(image, 0, 0, this);
        graphics.drawImage(image, 0, 0, this);
        graphics.finalize();
        isCanvasAltered = false;
        repaint();
    }

    /**
     * checks to see if the user has altered the canvas
     *
     * @return
     */
    public boolean isCanvasAltered() {
        return isCanvasAltered;
    }

    /**
     * sets whether the canvas has been altered
     *
     * @param canvasAltered
     */
    public void setCanvasAltered(boolean canvasAltered) {
        isCanvasAltered = canvasAltered;
    }

    /**
     * Redraw all canvas coordinates to the average of the coordinate's RGB values.
     */
    public void redrawToGreyscale() {
        int lumaValue;
        Color color_at_point;

        // Redraw all of the layers to greyscale
        for (BufferedImage layer : layers) {
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
        drawLayersOntoCanvas(layers, canvas);
        repaint();
    }

    /**
     * Draw random colourful noise on the canvas.
     */
    public void colouredNoiseGenerator() {
        Color color_at_point;
        for (BufferedImage layer : layers) {
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
        drawLayersOntoCanvas(layers, canvas);
        repaint();
    }

}
