package com.teambeta.sketcherapp.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * Class for drawable canvas.
 * Taken from "[Java] How to make a Swing Paint and Drawing application", Sylvain Saurel -
 * https://www.youtube.com/watch?v=OOb1eil4PCo
 */
public class DrawArea extends JComponent {
    private Image canvas;
    private Graphics2D graphics;
    private int currentX;
    private int currentY;
    private int oldX;
    private int oldY;


    /**
     * Constructor. Set actions upon mouse press events.
     */
    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coordinate x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coordinates x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (graphics != null) {
                    // draw line if graphics context not null
                    graphics.drawLine(oldX, oldY, currentX, currentY);
                    // refresh draw area to repaint
                    repaint();
                    // store current coordinates x,y as oldX, oldY
                    oldX = currentX;
                    oldY = currentY;
                }
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
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setPaint(Color.black);
        repaint();
    }

    /**
     * Set pen tool to black.
     */
    public void black() {
        graphics.setPaint(Color.black);
    }
}
