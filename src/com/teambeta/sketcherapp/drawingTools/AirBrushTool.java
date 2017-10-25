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
    private Graphics2D layer1Graphics;
    private int dotsToDraw;
    private int dotDiameter;
    private int dotX;
    private int dotY;
    private final double DOT_WIDTH_RATIO = 0.50;
    private final int DEFAULT_DOT_DIAMETER = 20;
    private final int DEFAULT_DOTS_TO_DRAW = 10;
    private final int DEFAULT_STOKE_VALUE = 10;

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
    public void onDrag(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        drawDotsAroundPoint(canvas, layers, drawingLayers);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
        drawDotsAroundPoint(canvas, layers, drawingLayers);
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        // Initialize canvas settings that the tool will require.
        initLayer1Graphics(canvas, layers, e);

        // Set the coordinates to the current point when the mouse is pressed.
        currentX = e.getX();
        currentY = e.getY();
    }

    /**
     * Generate random dots within the area of the unit circle bounded by the radius to the mouse cursor.
     */
    private void drawDotsAroundPoint(BufferedImage canvas, BufferedImage[] layers, LinkedList<ImageLayer> drawingLayers) {
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

        Graphics2D selectedLayerGraphics = null;
        if (selectedLayer != null) {
            selectedLayerGraphics = getGraphics2D(selectedLayer);

            for (int i = 0; i < dotsToDraw; ++i) {
                dot_angle = GeneratorFunctions.randomDouble(0, 2 * Math.PI);
                rand_radius = GeneratorFunctions.randomInt(-dotDiameter / 2, dotDiameter / 2);
                dotX = (int) (currentX + rand_radius * Math.sin(dot_angle));
                dotY = (int) (currentY + rand_radius * Math.cos(dot_angle));
                layer1Graphics.drawLine(dotX, dotY, dotX, dotY);
                selectedLayerGraphics.drawLine(dotX, dotY, dotX, dotY);
            }
            DrawArea.drawLayersOntoCanvas(layers, canvas);
            //DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
    }

    private Graphics2D getGraphics2D(ImageLayer selectedLayer) {
        Graphics2D selectedLayerGraphics;
        selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
        selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        selectedLayerGraphics.setColor(color);
        selectedLayerGraphics.setStroke(new BasicStroke((int) (DEFAULT_STOKE_VALUE * DOT_WIDTH_RATIO),
                BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return selectedLayerGraphics;
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
     *
     * @param canvas for drawing the line onto.
     * @param layers first layer by default is layers[0]
     * @param e      MouseEvent
     */
    private void initLayer1Graphics(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        layer1Graphics.setStroke(new BasicStroke((int) (DEFAULT_STOKE_VALUE * DOT_WIDTH_RATIO), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
    }

    @Override
    public void setFillState(boolean fillState) {

    }

}
