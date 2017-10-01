package com.teambeta.sketcherapp.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Class for drawable canvas.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private BufferedImage canvas;
    private BufferedImage[] layers = new BufferedImage[1];
    private Graphics2D graphics;
    private Color backgroundColor;


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
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                MainUI.selectedDrawingTool.onPress(graphics, e);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainUI.selectedDrawingTool.onRelease(canvas, layers, e);
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

    public static void clearBufferImage(BufferedImage bufferedImage) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setComposite(AlphaComposite.Src);
        Color transparentColor = new Color(0x00FFFFFF, true);
        graphics.setColor(transparentColor);
        graphics.setBackground(transparentColor);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

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
        graphics.setPaint(Color.white);
        // draw white on entire draw area to clear
        graphics.fillRect(0, 0, MainUI.CANVAS_WIDTH, MainUI.CANVAS_HEIGHT);
        graphics.setPaint(Color.black);
        repaint();
    }

    /**
     * Set pen tool to black.
     */
    public void black() {
        graphics.setPaint(Color.black);
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

}
