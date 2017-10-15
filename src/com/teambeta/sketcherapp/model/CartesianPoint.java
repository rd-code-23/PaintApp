package com.teambeta.sketcherapp.model;

/**
 * The cartesian point class which holds data of a point on the x-axis and y-axis.
 * It can hold the previous position of the point.
 */
public class CartesianPoint {

    private int xCurrent;
    private int yCurrent;
    private int xPrevious;
    private int yPrevious;

    /**
     * The constructor initializes the member fields to a default value.
     */
    public CartesianPoint() {
        xCurrent = 0;
        yCurrent = 0;
        xPrevious = 0;
        yPrevious = 0;
    }

    /**
     * Set the current point data
     *
     * @param x The x-axis value
     * @param y The y-axis value
     */
    public void setCurrent(int x, int y) {
        xCurrent = x;
        yCurrent = y;
    }

    /**
     * @return The current x-axis value
     */
    public int getXCurrent() {
        return xCurrent;
    }

    /**
     * @return The current y-axis value
     */
    public int getYCurrent() {
        return yCurrent;
    }

    /**
     * Explicitly set the previous point data
     *
     * @param x The previous x-axis value
     * @param y The previous y-axis value
     */
    public void setPrevious(int x, int y) {
        xPrevious = x;
        yPrevious = y;
    }

    /**
     * @return The previous x-axis value
     */
    public int getXPrevious() {
        return xPrevious;
    }

    /**
     * @return The previous y-axis value
     */
    public int getYPrevious() {
        return yPrevious;
    }

    /**
     * Set the previous point values to the current point values.
     */
    public void setPreviousFromCurrent() {
        xPrevious = xCurrent;
        yPrevious = yCurrent;
    }

}
