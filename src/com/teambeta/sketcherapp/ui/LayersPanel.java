package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.LinkedList;

public class LayersPanel extends JPanel implements ListSelectionListener {

    private DrawArea drawArea;
    private LinkedList<ImageLayer> drawingLayers;
    private JList<ImageLayer> listOfLayers = new JList<>();
    private DefaultListModel<ImageLayer> listModel = new DefaultListModel<>();
    private JScrollPane layersScrollPane;

    public JList<ImageLayer> getListOfLayers() {
        return listOfLayers;
    }

    public void setListOfLayers(JList<ImageLayer> listOfLayers) {
        this.listOfLayers = listOfLayers;
    }

    public LayersPanel(DrawArea drawArea) {
        super(new BorderLayout());
        this.drawArea = drawArea;
        this.drawingLayers = drawArea.getDrawingLayers();
        listOfLayers.setModel(listModel);
        listOfLayers.setVisibleRowCount(-1);
        listOfLayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfLayers.addListSelectionListener(this);
        layersScrollPane = new JScrollPane(listOfLayers);
        layersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        layersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(layersScrollPane, BorderLayout.CENTER);
    }

    public DefaultListModel<ImageLayer> getListModel() {
        return listModel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            drawArea.setCurrentlySelectedLayer(drawingLayers.get(listOfLayers.getSelectedIndex()));
            System.out.println(listOfLayers.getSelectedIndex());
        }
        drawArea.repaint();
    }
}
