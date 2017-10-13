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
    private int waveWidth;
    
    private final double TWO_PI = 2 * Math.PI;
    private final double DEFAULT_AMPLITUDE = 100;
    private final double DEFAULT_PERIOD = 350;
    private final int DEFAULT_WAVE_WIDTH = 65;

    private boolean firstOverlapping;
    private boolean secondOverlapping;
    private boolean thirdOverlapping;

    private CartesianPoint firstWaveUpper;
    private CartesianPoint firstWaveLower;
    private CartesianPoint secondWaveUpper;
    private CartesianPoint secondWaveLower;
    private CartesianPoint thirdWaveUpper;
    private CartesianPoint thirdWaveLower;
    private CartesianPoint[] pointContainer;

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
        waveWidth = DEFAULT_WAVE_WIDTH;

        firstWaveUpper = new CartesianPoint();
        firstWaveLower = new CartesianPoint();
        secondWaveUpper = new CartesianPoint();
        secondWaveLower = new CartesianPoint();
        thirdWaveUpper = new CartesianPoint();
        thirdWaveLower = new CartesianPoint();

        pointContainer = new CartesianPoint[6];
        pointContainer[0] = firstWaveUpper;
        pointContainer[1] = firstWaveLower;
        pointContainer[2] = secondWaveUpper;
        pointContainer[3] = secondWaveLower;
        pointContainer[4] = thirdWaveUpper;
        pointContainer[5] = thirdWaveLower;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        firstWaveUpper.setCurrent(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * (double) currentX)));
        firstWaveLower.setCurrent(currentX, firstWaveUpper.getYCurrent() - waveWidth);

        secondWaveUpper.setCurrent(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * ((double) currentX - DEFAULT_PERIOD/3))));
        secondWaveLower.setCurrent(currentX, secondWaveUpper.getYCurrent() - waveWidth);

        thirdWaveUpper.setCurrent(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * ((double) currentX - 2*DEFAULT_PERIOD/3))));
        thirdWaveLower.setCurrent(currentX, thirdWaveUpper.getYCurrent() - waveWidth);

        for (CartesianPoint point : pointContainer) {
            layer1Graphics.drawLine(point.getXPrevious(), point.getYPrevious(),
                                    point.getXCurrent(), point.getYCurrent());
            point.setPreviousFromCurrent();
        }

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

        firstWaveUpper.setPrevious(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * (double) currentX)));
        firstWaveLower.setPrevious(currentX, firstWaveUpper.getYPrevious() - waveWidth);

        secondWaveUpper.setPrevious(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * ((double) currentX - DEFAULT_PERIOD/3))));
        secondWaveLower.setPrevious(currentX, secondWaveUpper.getYPrevious() - waveWidth);

        thirdWaveUpper.setPrevious(currentX,
                currentY + (int)(amplitude * Math.sin(bValue * ((double) currentX - 2*DEFAULT_PERIOD/3))));
        thirdWaveLower.setPrevious(currentX, thirdWaveUpper.getYPrevious() - waveWidth);
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
