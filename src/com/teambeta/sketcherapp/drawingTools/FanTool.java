package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The FanTool class implements the drawing behavior for when the Fan tool has been selected
 *
 * NOTE: For nice looking "3D", draw using line width of 0.
 */
public class FanTool extends DrawingTool {

    private int x_start;
    private int y_start;
    private int x_current;
    private int y_current;
    private int lineWidth;
    private final int DEFAULT_STOKE_VALUE = 10;
    private Color lineColor;
    private Graphics2D layer1Graphics;

    /**
     * Constructor.
     */
    public FanTool() {
        lineColor = Color.BLACK;
        x_start = 0;
        y_start = 0;
        x_current = 0;
        y_current = 0;
        lineWidth = DEFAULT_STOKE_VALUE;
        registerObservers();
    }

    @Override
    public Color getColor() {
        return ColorChooser.getColor();
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        x_current = e.getX();
        y_current = e.getY();
        layer1Graphics.drawLine(x_start, y_start, x_current, y_current);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        initLayer1Graphics(canvas, layers, e);
        x_start = e.getX();
        y_start = e.getY();
    }

    @Override
    public int getToolWidth() {
        return lineWidth;
    }

    @Override
    public void setToolWidth(int width) {
        lineWidth = width;

    }

    @Override
    public void setFillState(boolean fillState) {

    }
    
    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                lineColor = ColorChooser.getColor();
            }
        });
    }

    /**
     * Initialize the parameters required for layer1Graphics.
     *
     * @param canvas for drawing the line onto.
     * @param layers first layer by default is layers[0]
     * @param e      MouseEvent
     */
    private void initLayer1Graphics(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(lineColor);
        layer1Graphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
    }

}
