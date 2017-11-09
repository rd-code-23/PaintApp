package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The RectangleTool class implements the drawing behavior for when the Rectangle tool has been selected
 * <p>
 * Behaviour of the rectangle tool:
 * - The longest side will take the length of the shortest side.
 * - The end-point relative to the init-point can be in any 4 quadrants.
 * - Draw a square when the shift button is held on mouse release.
 */
public class RectangleSelectionTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int mouseOriginX;
    private int mouseOriginY;
    private int drawWidthX;
    private int drawHeightY;
    private int xAxisMagnitudeDelta;
    private int yAxisMagnitudeDelta;
    private Color color;
    private BufferedImage previewLayer = null;
    private int squareWidth;
    private final int DEFAULT_WIDTH_VALUE = 10;
    private boolean fillShape;
    private float dash[] = {10.0f};
    /**
     * The constructor sets the properties of the tool to their default values
     */
    public RectangleSelectionTool() {
        registerObservers();
        color = Color.black;
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        mouseOriginX = 0;
        mouseOriginY = 0;
        drawWidthX = 0;
        drawHeightY = 0;
        xAxisMagnitudeDelta = 0;
        yAxisMagnitudeDelta = 0;
        squareWidth = DEFAULT_WIDTH_VALUE;
        fillShape = false;
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        if (previewLayer == null) {
            previewLayer = DrawArea.getPreviewBufferedImage();
        }
        //clear preview layer
        DrawArea.clearBufferImageToTransparent(previewLayer);

        //init graphics objects
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);
        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);
        previewLayerGraphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

        calcSquareCoordinateData(e);
        //draw the square preview onto its layer.
        // Draw a filled rectangle/square if the alt key is down on release.
        if (fillShape) {
            previewLayerGraphics.fillRect(initX, initY, drawWidthX, drawHeightY);
        }
        previewLayerGraphics.drawRect(initX, initY, drawWidthX, drawHeightY);
        //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        //draw the preview layer on top of the drawing layer(s)
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }

    private ImageLayer getSelectedLayer(LinkedList<ImageLayer> drawingLayers) {
        //get the selected layer, this assumes there is only one selected layer.
        for (int i = 0; i < drawingLayers.size(); i++) {
            ImageLayer drawingLayer = drawingLayers.get(i);
            if (drawingLayer.isSelected()) {
                return drawingLayer;
            }
        }
        return null;
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e,
                          LinkedList<ImageLayer> drawingLayers) {
        calcSquareCoordinateData(e);
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
            selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            selectedLayerGraphics.setColor(color);
            selectedLayerGraphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            // Draw a filled rectangle/square if the alt key is down on release.
            if (fillShape) {
                selectedLayerGraphics.fillRect(initX, initY, drawWidthX, drawHeightY);
            }
            selectedLayerGraphics.drawRect(initX, initY, drawWidthX, drawHeightY);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
    }

    private void calcSquareCoordinateData(MouseEvent e) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        // Draw the square with the longest side as long as the shortest side.
        xAxisMagnitudeDelta = Math.abs(currentX - mouseOriginX);
        yAxisMagnitudeDelta = Math.abs(currentY - mouseOriginY);

        // Detect shift-down by the MouseEvent, e.
        if (e.isShiftDown()) {
            if (xAxisMagnitudeDelta > yAxisMagnitudeDelta) {
                drawWidthX = yAxisMagnitudeDelta;
                drawHeightY = yAxisMagnitudeDelta;
            } else {
                drawWidthX = xAxisMagnitudeDelta;
                drawHeightY = xAxisMagnitudeDelta;
            }
        } else {
            drawWidthX = xAxisMagnitudeDelta;
            drawHeightY = yAxisMagnitudeDelta;
        }

        // Handle cases where the square lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < mouseOriginY) {
            initY = mouseOriginY - drawHeightY;
        }
        if (currentX < mouseOriginX) {
            initX = mouseOriginX - drawWidthX;
        }
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        canvas.getGraphics().setColor(color);
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
        mouseOriginX = currentX;
        mouseOriginY = currentY;
    }

    @Override
    public int getToolWidth() {
        return squareWidth;
    }

    @Override
    public void setToolWidth(int width) {
        squareWidth = width;
    }

    /**
     * getColor returns the current color the square tool is set to.
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

    public void setFillState(boolean fillState) {
        fillShape = fillState;
    }
}