package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.CartesianPoint;
import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The CelticKnotTool class implements the drawing behavior for when the Celtic Knot tool has been selected.
 *
 * The amplitude and period values are currently fixed numbers. Requires dedicated UI to update.
 */
public class CelticKnotTool extends DrawingTool {

    private double amplitude;
    private double bValue;
    
    private final double TWO_PI = 2 * Math.PI;
    private final double DEFAULT_AMPLITUDE = 100;
    private final double DEFAULT_PERIOD = 350;

    private CartesianPoint sine;
    private CartesianPoint negativeSine;
    private CartesianPoint cosine;
    private CartesianPoint negativeCosine;

    private int currentY;
    private int currentX;
    private Color color;
    private int brushWidth;
    private Graphics2D layer1Graphics;
    private final int DEFAULT_STOKE_VALUE = 10;

        /**
         * The constructor sets the properties of the tool to their default values
         */
    public CelticKnotTool() {
        registerObservers();
        color = Color.black;
        currentX = 0;
        currentY = 0;
        amplitude = DEFAULT_AMPLITUDE;
        bValue = TWO_PI / DEFAULT_PERIOD;
        brushWidth = DEFAULT_STOKE_VALUE;

        sine = new CartesianPoint();
        negativeSine = new CartesianPoint();
        cosine = new CartesianPoint();
        negativeCosine = new CartesianPoint();
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        sine.setCurrent(currentX, currentY + (int)(amplitude * Math.sin(bValue * (double) currentX)));
        negativeSine.setCurrent(currentX, currentY - (int)(amplitude * Math.sin(bValue * (double) currentX)));
        cosine.setCurrent(currentX, currentY + (int)(amplitude * Math.cos(bValue * (double) currentX)));
        negativeCosine.setCurrent(currentX, currentY - (int)(amplitude * Math.cos(bValue * (double) currentX)));

        // Sine wave
        layer1Graphics.drawLine(sine.getXPrevious(), sine.getYPrevious(),
                                sine.getXCurrent(), sine.getYCurrent());
        // Negative sine wave
        layer1Graphics.drawLine(negativeSine.getXPrevious(), negativeSine.getYPrevious(),
                                negativeSine.getXCurrent(), negativeSine.getYCurrent());
        // Cosine wave
        layer1Graphics.drawLine(cosine.getXPrevious(), cosine.getYPrevious(),
                                cosine.getXCurrent(), cosine.getYCurrent());
        // Negative cosine wave
        layer1Graphics.drawLine(negativeCosine.getXPrevious(), negativeCosine.getYPrevious(),
                                negativeCosine.getXCurrent(), negativeCosine.getYCurrent());

        sine.setPreviousFromCurrent();
        negativeSine.setPreviousFromCurrent();
        cosine.setPreviousFromCurrent();
        negativeCosine.setPreviousFromCurrent();

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
        // Initialize canvas settings that the tool will require.
        initLayer1Graphics(canvas, layers, e);
        currentX = e.getX();
        currentY = e.getY();

        // Set the start of the sinusoidal waves as a function of the initial press location.
        sine.setPrevious(currentX, currentY + (int)(amplitude * Math.sin(bValue * (double) currentX)));
        negativeSine.setPrevious(currentX, currentY - (int)(amplitude * Math.sin(bValue * (double) currentX)));
        cosine.setPrevious(currentX, currentY + (int)(amplitude * Math.cos(bValue * (double) currentX)));
        negativeCosine.setPrevious(currentX, currentY - (int)(amplitude * Math.cos(bValue * (double) currentX)));
    }

    /**
     * getColor returns the current color the brush tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
    }

    public int getToolWidth() {
        return brushWidth;
    }

    public void setToolWidth(int brushWidth) {
        this.brushWidth = brushWidth;
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class.
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
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
        layer1Graphics.setColor(color);
        layer1Graphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
    }

    @Override
    public void setFillState(boolean fillState) {

    }
}
