package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The BrushTool class implements the drawing behavior for when the Brush tool has been selected
 */
public class AirBrushTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private Color color;
    private int brushWidth;
    private Graphics2D layer1Graphics;
    private int dotsToDraw;
    private int dotRadius;
    private int dotX;
    private int dotY;
    private final double DOT_WIDTH_RATIO = 0.50;
    private final int DEFAULT_DOT_RADIUS = 20;
    private final int DEFAULT_DOTS_TO_DRAW = 20;
    private final int DEFAULT_STOKE_VALUE = 10;


    /**
     * The constructor sets the properties of the tool to their default values
     */
    public AirBrushTool() {
        color = Color.black;
        registerObservers();
        currentX = 0;
        currentY = 0;
        brushWidth = DEFAULT_STOKE_VALUE;
        dotRadius = DEFAULT_DOT_RADIUS;
        dotsToDraw = DEFAULT_DOTS_TO_DRAW;
        dotX = 0;
        dotY = 0;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        drawDotsAroundPoint();
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        // Initialize canvas settings that the tool will require.
        initLayer1Graphics(canvas, layers, e);

        // Set the coordinates to the current point when the mouse is pressed.
        currentX = e.getX();
        currentY = e.getY();

        drawDotsAroundPoint();
    }

    private void drawDotsAroundPoint() {
        for (int i = 0; i <= dotsToDraw; i++) {
            dotX = randomInt(currentX - dotRadius, currentX + dotRadius);
            dotY = randomInt(currentY - dotRadius, currentY + dotRadius);
            layer1Graphics.drawLine(dotX, dotY, dotX, dotY);
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

    public int getToolWidth() {
        return dotRadius;
    }

    public void setToolWidth(int brushWidth) {
        dotRadius = brushWidth;
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
