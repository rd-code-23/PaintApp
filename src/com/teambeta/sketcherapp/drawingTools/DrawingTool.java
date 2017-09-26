package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * DrawingTool defines the template methods for all drawing tools
 */
public abstract class DrawingTool {
    /**
     * @return Returns the color the drawing tool is set to.
     */
    public abstract Color getColor();

    /**
     * onDrag defines the graphical behavior of the tool when the mouse is dragged.
     *
     * @param graphics The graphics to draw onto
     * @param e        The mouse listenForSlider that needs to be responded to.
     */
    public abstract void onDrag(Graphics2D graphics, MouseEvent e);

    /**
     * onRelease defines the graphical behavior of the tool when the mouse is released.
     *
     * @param graphics The graphics to draw onto
     * @param e        The mouse listenForSlider that needs to be responded to.
     */
    public abstract void onRelease(Graphics2D graphics, MouseEvent e);

    /**
     * onClick defines the graphical behavior of the tool when the mouse is released.
     *
     * @param graphics The graphics to draw onto
     * @param e        The mouse listenForSlider that needs to be responded to.
     */
    public abstract void onClick(Graphics2D graphics, MouseEvent e);

    /**
     * onPress defines the graphical behavior of the tool when the mouse is released.
     *
     * @param graphics The graphics to draw onto
     * @param e        The mouse listenForSlider that needs to be responded to.
     */
    public abstract void onPress(Graphics2D graphics, MouseEvent e);
}
