package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

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
    private Color color;
    private BufferedImage previewLayer = null;
    private int lineWidth;
    private final int DEFAULT_WIDTH_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public LineTool() {
        color = Color.black;
        registerObservers();
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
        lineWidth = DEFAULT_WIDTH_VALUE;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        if (previewLayer == null) {
            previewLayer = DrawArea.getPreviewBufferedImage();
        }
        //clear preview layer
        DrawArea.clearBufferImageToTransparent(previewLayer);

        //init graphics objects
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setStroke(new BasicStroke(getToolWidth()));
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        previewLayerGraphics.setStroke(new BasicStroke(getToolWidth()));
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);

        //get the current end point for the line preview
        currentX = e.getX();
        currentY = e.getY();

        //draw the line preview onto its layer
        previewLayerGraphics.drawLine(lastX, lastY, currentX, currentY);

        //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        //draw the preview layer on top of the drawing layer(s)
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }


    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setStroke(new BasicStroke(getToolWidth()));
        layer1Graphics.setColor(this.getColor());
        //get the coordinates of where the user released the mouse
        currentX = e.getX();
        currentY = e.getY();
        //draw a line between the start and release points
        layer1Graphics.drawLine(lastX, lastY, currentX, currentY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //do nothing
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //set the coordinates to the current pixel clicked
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public int getToolWidth() {
        return lineWidth;
    }

    @Override
    public void setToolWidth(int width) {
        lineWidth = width;
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

    @Override
    public void setFillState(boolean fillState) {

    }
}
