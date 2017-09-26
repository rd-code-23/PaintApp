package com.teambeta.sketcherapp.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;

import javax.swing.JComponent;

/**
 * Class for drawable canvas.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private Image canvas;
    private Graphics2D graphics;

    /**
     * Constructor. Set actions upon mouse press events.
     */
    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainUI.selectedDrawingTool.onClick(graphics, e);
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
                MainUI.selectedDrawingTool.onRelease(graphics, e);
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                MainUI.selectedDrawingTool.onDrag(graphics, e);
                repaint();
            }
        });
    }

    /**
     * Creates a canvas for drawable elements.
     *
     * @param canvasGraphics graphics for canvas
     */
    protected void paintComponent(Graphics canvasGraphics) {
        if (canvas == null) {
            // create a canvas to draw null
            canvas = createImage(getSize().width, getSize().height);
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
    public Color getBackgroundColor() {
        return graphics.getBackground();
    }

}