package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The LineTool class implements the drawing behavior for when the Line tool has been selected
 */
public class LineTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public LineTool() {
        color = Color.black;
        registerObservers();
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
    }

    @Override
    public void onDrag(BufferedImage canvas, Graphics2D graphics, MouseEvent e) {

        BufferedImage previewLayer = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);

        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);

        currentX = e.getX();
        currentY = e.getY();
        previewLayerGraphics.drawLine(lastX, lastY, currentX, currentY);


        //source: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        //draw the preview layer on top of the drawing layer(s)
        canvasGraphics.setComposite(alphaComposite);


        canvasGraphics.drawImage(canvas, 0, 0, null);
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }


    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e) {
        //get the coordinates of where the user released the mouse
        currentX = e.getX();
        currentY = e.getY();
        //draw a line between the start and release points
        if (graphics != null) {
            // draw line if graphics context not null
            graphics.drawLine(lastX, lastY, currentX, currentY);
        }
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //do nothing
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        //set the coordinates to the current pixel clicked
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }

    /**
     * getColor returns the current color the line tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
            }
        });
    }
}
