package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.GeneratorFunctions;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The BrushTool class implements the drawing behavior for when the Brush tool has been selected
 */
public class AirBrushTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private Color color;
    private int dotsToDraw;
    private int dotDiameter;
    private int dotX;
    private int dotY;
    private static final double DOT_WIDTH_RATIO = 0.50;
    private static final int DEFAULT_DOT_DIAMETER = 20;
    private static final int DEFAULT_DOTS_TO_DRAW = 10;
    private static final int DEFAULT_STOKE_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values.
     */
    public AirBrushTool() {
        color = Color.black;
        registerObservers();
        currentX = 0;
        currentY = 0;
        dotDiameter = DEFAULT_DOT_DIAMETER;
        dotsToDraw = DEFAULT_DOTS_TO_DRAW;
        dotX = 0;
        dotY = 0;
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
        drawDotsAroundPoint(canvas, drawingLayers);
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
    }

    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        // Set the coordinates to the current point when the mouse is pressed.
        currentX = e.getX();
        currentY = e.getY();
        drawDotsAroundPoint(canvas, drawingLayers);
    }

    /**
     * Generate random dots within the area of the unit circle bounded by the radius to the mouse cursor.
     */
    private void drawDotsAroundPoint(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers) {
        double dot_angle;
        double rand_radius;

        //get the selected layer, this assumes there is only one selected layer.
        ImageLayer selectedLayer = null;
        for (int i = 0; i < drawingLayers.size(); i++) {
            ImageLayer drawingLayer = drawingLayers.get(i);
            if (drawingLayer.isSelected()) {
                selectedLayer = drawingLayer;
                break;
            }
        }

        Graphics2D selectedLayerGraphics;
        if (selectedLayer != null) {
            selectedLayerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            for (int i = 0; i < dotsToDraw; ++i) {
                dot_angle = GeneratorFunctions.randomDouble(0, 2 * Math.PI);
                rand_radius = GeneratorFunctions.randomInt(-dotDiameter / 2, dotDiameter / 2);
                dotX = (int) (currentX + rand_radius * Math.sin(dot_angle));
                dotY = (int) (currentY + rand_radius * Math.cos(dot_angle));
                selectedLayerGraphics.drawLine(dotX, dotY, dotX, dotY);
            }
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
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

    /**
     * Get width of the tool.
     *
     * @return dot's radius.
     */
    public int getToolWidth() {
        return dotDiameter;
    }

    /**
     * Set dot radius to brush width.
     *
     * @param brushWidth width to set dot radius to.
     */
    public void setToolWidth(int brushWidth) {
        dotDiameter = brushWidth;
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
     */
    private Graphics2D initLayerGraphics(BufferedImage layer) {
        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(color);
        layerGraphics.setStroke(new BasicStroke((int) (DEFAULT_STOKE_VALUE * DOT_WIDTH_RATIO),
                BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return layerGraphics;
    }

    @Override
    public void setFillState(boolean fillState) {
    }

}