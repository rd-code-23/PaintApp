package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.CartesianPoint;
import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The CelticKnotTool class implements the drawing behavior for when the Celtic Knot tool has been selected.
 * <p>
 * The amplitude and period values are currently fixed numbers. Requires dedicated UI to update.
 */
public class CelticKnotTool extends DrawingTool {

    private double amplitude;
    private double bValue;
    private int waveWidth;
    private int xDifferenceToOrigin;

    private final double TWO_PI = 2.0 * Math.PI;
    private final double DEFAULT_AMPLITUDE = 100.0;
    private final double DEFAULT_PERIOD = 350.0;
    private final double SECOND_WAVE_PHASE_SHIFT = DEFAULT_PERIOD / 3.0;
    private final double THIRD_WAVE_PHASE_SHIFT = 2.0 * DEFAULT_PERIOD / 3.0;
    private final int DEFAULT_WAVE_WIDTH = 65;

    private CartesianPoint firstWaveUpper;
    private CartesianPoint firstWaveLower;
    private CartesianPoint secondWaveUpper;
    private CartesianPoint secondWaveLower;
    private CartesianPoint thirdWaveUpper;
    private CartesianPoint thirdWaveLower;
    private CartesianPoint[] pointContainer;

    private int currentY;
    private int currentX;
    private int lastX;
    private Color color;
    private int brushWidth;
    private Graphics2D layer1Graphics;
    private final int DEFAULT_STOKE_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public CelticKnotTool() {
        registerObservers();
        color = Color.black;
        currentX = 0;
        currentY = 0;
        xDifferenceToOrigin = 0;
        amplitude = DEFAULT_AMPLITUDE;
        bValue = TWO_PI / DEFAULT_PERIOD;
        brushWidth = DEFAULT_STOKE_VALUE;
        waveWidth = DEFAULT_WAVE_WIDTH;

        firstWaveUpper = new CartesianPoint();
        firstWaveLower = new CartesianPoint();
        secondWaveUpper = new CartesianPoint();
        secondWaveLower = new CartesianPoint();
        thirdWaveUpper = new CartesianPoint();
        thirdWaveLower = new CartesianPoint();

        pointContainer = new CartesianPoint[6];
        pointContainer[0] = firstWaveUpper;
        pointContainer[1] = firstWaveLower;
        pointContainer[2] = secondWaveUpper;
        pointContainer[3] = secondWaveLower;
        pointContainer[4] = thirdWaveUpper;
        pointContainer[5] = thirdWaveLower;
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
        xDifferenceToOrigin += (currentX - lastX);
        incrementWave();
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics;
            selectedLayerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            for (CartesianPoint point : pointContainer) {
                selectedLayerGraphics.drawLine(point.getXPrevious(), point.getYPrevious(),
                        point.getXCurrent(), point.getYCurrent());
                point.setPreviousFromCurrent();
            }
            lastX = currentX;
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
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
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        xDifferenceToOrigin = 0;
        incrementWave();
        for (CartesianPoint point : pointContainer) {
            point.setPreviousFromCurrent();
        }
    }

    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

    }

    private void incrementWave() {
        firstWaveUpper.setCurrent(currentX,
                (currentY +
                        (int) (amplitude * Math.sin(bValue * (double) xDifferenceToOrigin))) + waveWidth / 2);
        firstWaveLower.setCurrent(currentX, firstWaveUpper.getYCurrent() - waveWidth);

        secondWaveUpper.setCurrent(currentX,
                (currentY +
                        (int) (amplitude * Math.sin(bValue * ((double) xDifferenceToOrigin - SECOND_WAVE_PHASE_SHIFT))))
                        + waveWidth / 2);
        secondWaveLower.setCurrent(currentX, secondWaveUpper.getYCurrent() - waveWidth);

        thirdWaveUpper.setCurrent(currentX,
                (currentY
                        + (int) (amplitude * Math.sin(bValue * ((double) xDifferenceToOrigin - THIRD_WAVE_PHASE_SHIFT))))
                        + waveWidth / 2);
        thirdWaveLower.setCurrent(currentX, thirdWaveUpper.getYCurrent() - waveWidth);
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
        return brushWidth;
    }

    public void setToolWidth(int brushWidth) {
        this.brushWidth = brushWidth;
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
        Graphics2D layerGraphics;
        layerGraphics = (Graphics2D) layer.getGraphics();
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(color);
        layerGraphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return layerGraphics;
    }

    @Override
    public void setFillState(boolean fillState) {
    }
}