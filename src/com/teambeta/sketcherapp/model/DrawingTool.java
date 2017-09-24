package com.teambeta.sketcherapp.model;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * DrawingTool defines the template methods for all drawing tools
 */
public abstract class DrawingTool {
    public abstract Color getColor();

    public abstract void onDrag(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY);

    public abstract void onRelease(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY);

    public abstract void onClick(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY);

    public abstract void onPress(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY);
}
