package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class LayersPanel extends JPanel implements ListSelectionListener {

    private final static String HIDE_SHOW_LAYER_BUTTON_TEXT = "Hide/Show Layer";
    private DrawArea drawArea;
    private LinkedList<ImageLayer> drawingLayers;
    private JList<ImageLayer> listOfLayers = new JList<>();
    private DefaultListModel<ImageLayer> listModel = new DefaultListModel<>();
    private JScrollPane layersScrollPane;
    private JButton hideShowLayerButton;

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
        layersScrollPane.setMaximumSize(new Dimension(400, 100));
        this.add(layersScrollPane, BorderLayout.CENTER);

        hideShowLayerButton = new JButton(HIDE_SHOW_LAYER_BUTTON_TEXT);
        ActionListener hideShowLayerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageLayer selectedLayer = drawArea.getDrawingLayers().get(listOfLayers.getSelectedIndex());
                selectedLayer.setVisible(!selectedLayer.isVisible());
                //TODO: add GUI element that shows the layer is hidden
                drawArea.refreshLayers();
                drawArea.repaint();
            }
        };
        hideShowLayerButton.setActionCommand(HIDE_SHOW_LAYER_BUTTON_TEXT);
        hideShowLayerButton.addActionListener(hideShowLayerActionListener);

        this.add(hideShowLayerButton, BorderLayout.SOUTH);
    }

    public DefaultListModel<ImageLayer> getListModel() {
        return listModel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            int selectedIndex = listOfLayers.getSelectedIndex();
            drawArea.setCurrentlySelectedLayer(drawingLayers.get(selectedIndex));
        }
        drawArea.repaint();
    }
}
