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
public class DoubleHelixTool extends DrawingTool {

    private double amplitude;
    private double bValue;
    private int waveWidth;
    private int xDifferenceToOrigin;

    private final double TWO_PI = 2.0 * Math.PI;
    private final double DEFAULT_AMPLITUDE = 100.0;
    private final double DEFAULT_PERIOD = 350.0;
    private final double SECOND_WAVE_PHASE_SHIFT = DEFAULT_PERIOD / 3.0;
    private final double THIRD_WAVE_PHASE_SHIFT = 2.0 * DEFAULT_PERIOD / 3.0;
    private final double EPSILON = 0.035;
    private final int DEFAULT_WAVE_WIDTH = 65;

    private CartesianPoint firstWaveUpper;
    private CartesianPoint firstWaveLower;
    private CartesianPoint[] pointContainer;

    private int currentY;
    private int currentX;
    private int lastX;
    private boolean inFirstPeriod;


    // Access bar by n-1;
    private boolean[] periodBars = {false, false, false, false, false, false};

    private Color color;
    private int brushWidth;
    private Graphics2D layer1Graphics;
    private final int DEFAULT_STOKE_VALUE = 10;

        /**
         * The constructor sets the properties of the tool to their default values
         */
    public DoubleHelixTool() {
        registerObservers();
        color = Color.black;
        currentX = 0;
        currentY = 0;
        xDifferenceToOrigin = 0;
        amplitude = DEFAULT_AMPLITUDE;
        bValue = TWO_PI / DEFAULT_PERIOD;
        brushWidth = DEFAULT_STOKE_VALUE;
        waveWidth = DEFAULT_WAVE_WIDTH;
        inFirstPeriod = false;

        firstWaveUpper = new CartesianPoint();
        firstWaveLower = new CartesianPoint();

        pointContainer = new CartesianPoint[2];
        pointContainer[0] = firstWaveUpper;
        pointContainer[1] = firstWaveLower;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        xDifferenceToOrigin += (currentX - lastX);

        firstWaveUpper.setCurrent(currentX,
                (currentY +
                        (int)(amplitude * Math.sin(bValue * (double) xDifferenceToOrigin))));
        firstWaveLower.setCurrent(currentX, (currentY -
                (int)(amplitude * Math.sin(bValue * (double) xDifferenceToOrigin))));

        double periodRatio = Math.abs(((xDifferenceToOrigin % DEFAULT_PERIOD) / DEFAULT_PERIOD));

        if (Math.abs(periodRatio) < 0.5) {
            // First half-period

            for (int i = 3; i < 6; ++i) {
                periodBars[i] = false;
            }

            if (!inFirstPeriod) {
                inFirstPeriod = true;
            }

            // Bar 1
            if (Math.abs(periodRatio - 0.15) < EPSILON && !getBarDrawn(1)) {
                setBarDrawn(1, true);
                drawLineBetweenWaves();
            }

            // Bar 2
            if (Math.abs(periodRatio - 0.25) < EPSILON && !getBarDrawn(2)) {
                setBarDrawn(2, true);
                drawLineBetweenWaves();
            }

            // Bar 3
            if (Math.abs(periodRatio - 0.35) < EPSILON && !getBarDrawn(3)) {
                setBarDrawn(3, true);
                drawLineBetweenWaves();
            }

        } else {
            for (int i = 0; i < 3; ++i) {
                periodBars[i] = false;
            }

            // Second half-period
            if (inFirstPeriod) {
                inFirstPeriod = false;
            }

            // Bar 4
            if (Math.abs(periodRatio - 0.65) < EPSILON && !getBarDrawn(4)) {
                setBarDrawn(4, true);
                drawLineBetweenWaves();
            }

            // Bar 5
            if (Math.abs(periodRatio - 0.75) < EPSILON && !getBarDrawn(5)) {
                setBarDrawn(5, true);
                drawLineBetweenWaves();
            }

            // Bar 6
            if (Math.abs(periodRatio - 0.85) < EPSILON && !getBarDrawn(6)) {
                setBarDrawn(6, true);
                drawLineBetweenWaves();
            }

        }
        // FIRST LOOP
        if (Math.abs(periodRatio - 0.25) < 0.01) {

        }

        // SECOND LOOP

        for (CartesianPoint point : pointContainer) {
            layer1Graphics.drawLine(point.getXPrevious(), point.getYPrevious(),
                                    point.getXCurrent(), point.getYCurrent());
            point.setPreviousFromCurrent();
        }

        lastX = currentX;

        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        xDifferenceToOrigin = 0;
        inFirstPeriod = false;

        for (int i = 0; i < periodBars.length; ++i) {
            periodBars[i] = false;
        }
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
        lastX = currentX;
        xDifferenceToOrigin = 0;

        firstWaveUpper.setCurrent(currentX, currentY);
        firstWaveLower.setCurrent(currentX, currentY);

        inFirstPeriod = false;
        for (CartesianPoint point : pointContainer) {
            point.setPreviousFromCurrent();
        }
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

    private void drawLineBetweenWaves() {
        layer1Graphics.drawLine(firstWaveLower.getXCurrent(), firstWaveLower.getYCurrent(),
                                firstWaveUpper.getXCurrent(), firstWaveUpper.getYCurrent());
    }

    private boolean getBarDrawn(int bar) {
        return periodBars[bar - 1];
    }

    private void setBarDrawn(int bar, boolean state) {
        periodBars[bar - 1] = state;
    }

}
