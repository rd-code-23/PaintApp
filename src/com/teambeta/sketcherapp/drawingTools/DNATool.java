package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.CartesianPoint;
import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The DNATool class implements the drawing behavior for when the DNATool has been selected.
 *
 * The amplitude and period values are currently fixed numbers. Requires dedicated UI to update.
 */
public class DNATool extends DrawingTool {

    private final double TWO_PI = 2.0 * Math.PI;
    private final double DEFAULT_AMPLITUDE = 100.0;
    private final double DEFAULT_PERIOD = 400.0;
    private final double HALF_PERIOD_RATIO = 0.5;
    private final double LOWER_WAVE_PHASE_SHIFT = DEFAULT_PERIOD / 3;
    private final double BAR_REDUCTION_FACTOR = 0.75;
    private final int FIRST_HALF_PERIOD_BAR_START_INDEX = 0;
    private final int FIRST_HALF_PERIOD_BAR_END_INDEX = 3;
    private final int SECOND_HALF_PERIOD_BAR_START_INDEX = 4;
    private final int SECOND_HALF_PERIOD_BAR_END_INDEX = 7;
    private final int MAXIMUM_ALLOWABLE_X_AXIS_DRIFT = 5;

    private CartesianPoint upperWave;
    private CartesianPoint lowerWave;
    private CartesianPoint[] pointContainer;

    private int currentX;
    private int currentY;
    private int lastX;
    private int xDifferenceToOrigin;
    private double amplitude;
    private double bValue;
    private double currentPeriodRatio;
    private boolean inFirstHalfPeriod;
    private boolean wasGoingRight;

    int switchPointX;
    boolean switchPointSet = false;

    // Access bar by n-1;
    private boolean[] periodBars = {false, false, false, false,
            false, false, false, false};
    // IMPORTANT PERIODIC RATIOS FOR BAR LOCATIONS. DO NOT CHANGE.
    private final double[] leftToRightBarRatios = {0.02, 0.12, 0.22, 0.32,
            0.52, 0.62, 0.72, 0.82};
    private double[] rightToLeftBarRatios = {0.17, 0.27, 0.37, 0.47,
            0.67, 0.77, 0.87, 0.97};

    private Color color;
    private int brushWidth;
    private Graphics2D layer1Graphics;
    private final int DEFAULT_STOKE_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public DNATool() {

        registerObservers();
        color = Color.black;
        currentX = 0;
        currentY = 0;
        xDifferenceToOrigin = 0;
        currentPeriodRatio = 0;
        amplitude = DEFAULT_AMPLITUDE;
        bValue = TWO_PI / DEFAULT_PERIOD;
        brushWidth = DEFAULT_STOKE_VALUE;
        inFirstHalfPeriod = false;

        upperWave = new CartesianPoint();
        lowerWave = new CartesianPoint();

        pointContainer = new CartesianPoint[2];
        pointContainer[0] = upperWave;
        pointContainer[1] = lowerWave;

    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        xDifferenceToOrigin += (currentX - lastX);

        upperWave.setCurrent(currentX,
                (currentY +
                        (int)(amplitude * Math.sin(bValue * (double) xDifferenceToOrigin))));
        lowerWave.setCurrent(currentX,
                (currentY +
                        (int)(amplitude * Math.sin(bValue * ((double) xDifferenceToOrigin - LOWER_WAVE_PHASE_SHIFT)))));

        currentPeriodRatio = Math.abs(((xDifferenceToOrigin % DEFAULT_PERIOD) / DEFAULT_PERIOD));

        // Reset tool state if the direction changes and the difference is greater than a certain requirement.
        if (wasGoingRight != (currentX >= lastX)) {
            if (!switchPointSet) {
                switchPointX = currentX;
                switchPointSet = true;
            }

            if (switchPointSet && ((Math.abs(currentX - switchPointX) > MAXIMUM_ALLOWABLE_X_AXIS_DRIFT))) {
                for (int i = FIRST_HALF_PERIOD_BAR_START_INDEX; i <= SECOND_HALF_PERIOD_BAR_END_INDEX; ++i) {
                    periodBars[i] = false;
                }
                currentPeriodRatio = 0.0;
                xDifferenceToOrigin = 0;
                inFirstHalfPeriod = true;
                switchPointSet = false;
            }
        }

        wasGoingRight = currentX >= lastX;

        if (Math.abs(currentPeriodRatio) <= HALF_PERIOD_RATIO) {
            // First half-period

            // Prepare the second half-period for drawing.
            for (int i = SECOND_HALF_PERIOD_BAR_START_INDEX; i <= SECOND_HALF_PERIOD_BAR_END_INDEX; ++i) {
                periodBars[i] = false;
            }

            if (!inFirstHalfPeriod) {
                inFirstHalfPeriod = true;
            }

            for (int i = FIRST_HALF_PERIOD_BAR_START_INDEX; i <= FIRST_HALF_PERIOD_BAR_END_INDEX; ++i) {
                drawLegalBar(i, currentPeriodRatio);
            }

        } else {
            // Second half-period

            // Prepare the first half-period for drawing.
            for (int i = FIRST_HALF_PERIOD_BAR_START_INDEX; i <= FIRST_HALF_PERIOD_BAR_END_INDEX; ++i) {
                periodBars[i] = false;
            }

            if (inFirstHalfPeriod) {
                inFirstHalfPeriod = false;
            }

            for (int i = SECOND_HALF_PERIOD_BAR_START_INDEX; i <= SECOND_HALF_PERIOD_BAR_END_INDEX; ++i) {
                drawLegalBar(i, currentPeriodRatio);
            }
        }

        // Finally, draw the computed sinusoidal lines.
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
        inFirstHalfPeriod = false;

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
        currentPeriodRatio = 0;

        upperWave.setCurrent(currentX, currentY);
        lowerWave.setCurrent(currentX, currentY);

        inFirstHalfPeriod = false;
        for (CartesianPoint point : pointContainer) {
            point.setPreviousFromCurrent();
        }
    }

    /**
     * getColor returns the current color that tool is set to.
     *
     * @return The current tool color
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
    }

    /**
     * getToolWidth returns the current width that the tool is set to.
     * @return The current tool width
     */
    public int getToolWidth() {
        return brushWidth;
    }

    /**
     * setToolWidth sets the tool width
     * @param brushWidth The tool width
     */
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
        layer1Graphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,
                BasicStroke.CAP_BUTT));
    }

    @Override
    public void setFillState(boolean fillState) {

    }

    /**
     * Draw a line between the sine waves at their current render point.
     * This line will be ~75% the brush width of the main wave.
     */
    private void drawBarBetweenWaves() {
        int barMiddleY = (lowerWave.getYCurrent() + upperWave.getYCurrent()) / 2;
        int originalBrushWidth = getToolWidth();
        double barBrushWidth = originalBrushWidth * BAR_REDUCTION_FACTOR; // Expected integer precision loss.

        Color originalColor = getColor();
        Color[] atcg_colors = {Color.red, Color.green, Color.blue, Color.yellow};

        // Set the color and brush width profiles for ATCG mode.
        layer1Graphics.setStroke(new BasicStroke((int) barBrushWidth, BasicStroke.CAP_SQUARE,
                BasicStroke.CAP_BUTT));
        layer1Graphics.setColor(atcg_colors[randomInt(0, atcg_colors.length - 1)]);

        // Draw the lower half of the bar with a new color.
        layer1Graphics.drawLine(lowerWave.getXCurrent(), lowerWave.getYCurrent(),
                upperWave.getXCurrent(), barMiddleY);

        layer1Graphics.setColor(atcg_colors[randomInt(0, atcg_colors.length - 1)]);

        // Draw the upper half of the bar with another color.
        layer1Graphics.drawLine(lowerWave.getXCurrent(), barMiddleY,
                upperWave.getXCurrent(), upperWave.getYCurrent());

        // Return to the normal color and brush width profiles.
        layer1Graphics.setColor(originalColor);
        layer1Graphics.setStroke(new BasicStroke(originalBrushWidth, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_BUTT));
    }

    /**
     * Draw a bar between the waves given that it is allowed to.
     * @param bar_index The bar index number
     * @param period_ratio The period ratio at the current point
     */
    private void drawLegalBar(int bar_index, double period_ratio) {
        if (wasGoingRight) {
            if (((Math.abs(period_ratio) >= leftToRightBarRatios[bar_index])) && !(periodBars[bar_index])) {
                periodBars[bar_index] = true;
                drawBarBetweenWaves();

            }
        } else {
            if (((Math.abs(period_ratio) >= rightToLeftBarRatios[bar_index])) && !(periodBars[bar_index])) {
                periodBars[bar_index] = true;
                drawBarBetweenWaves();
            }
        }
    }

    /**
     * Return a random integer within the closed interval of min to max.
     *
     * @param min The minimum number
     * @param max The maximum number
     * @return The random number from min to max
     */
    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

}
