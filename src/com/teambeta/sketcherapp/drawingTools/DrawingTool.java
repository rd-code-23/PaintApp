package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * DrawingTool defines the template methods for all drawing tools
 */
public abstract class DrawingTool {
    public abstract Color getColor();

    public abstract void onDrag(Graphics2D graphics, MouseEvent e);

    public abstract void onRelease(Graphics2D graphics, MouseEvent e);

    public abstract void onClick(Graphics2D graphics, MouseEvent e);

    public abstract void onPress(Graphics2D graphics, MouseEvent e);
}
