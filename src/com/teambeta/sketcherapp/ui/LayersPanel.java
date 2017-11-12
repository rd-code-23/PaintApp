package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class LayersPanel extends JPanel implements ListSelectionListener {

    private static final String HIDE_SHOW_LAYER_BUTTON_TEXT = "Hide/Show";
    private static final String ADD_LAYER_BUTTON_TEXT = "Add";
    private static final String DELETE_LAYER_BUTTON_TEXT = "Delete";
    private static final int MAX_NUM_OF_LAYERS = 10;
    private DrawArea drawArea;
    private LinkedList<ImageLayer> drawingLayers;
    private JList<ImageLayer> listOfLayers = new JList<>();
    private DefaultListModel<ImageLayer> listModel = new DefaultListModel<>();
    private JScrollPane layersScrollPane;
    private JButton hideShowLayerButton;
    private JButton addLayerButton;
    private JButton deleteLayerButton;

    /**
     * Return The JList of ImageLayers stored in the LayersPanel
     *
     * @return The stored JList of ImageLayers.
     */
    public JList<ImageLayer> getListOfLayers() {
        return listOfLayers;
    }

    /**
     * The constructor for the LayersPanel class.
     *
     * @param drawArea The drawArea the layersPanel is utilizing.
     */
    public LayersPanel(DrawArea drawArea) {
        super(new BorderLayout());
        this.drawArea = drawArea;
        this.drawingLayers = drawArea.getDrawingLayers();
        listOfLayers.setFixedCellWidth(270);
        listOfLayers.setFixedCellHeight(150);
        listOfLayers.setModel(listModel);
        listOfLayers.setVisibleRowCount(-1);
        listOfLayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfLayers.addListSelectionListener(this);
        layersScrollPane = new JScrollPane(listOfLayers);
        layersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        layersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(layersScrollPane, BorderLayout.EAST);
        addLayerButtons(drawArea);
    }

    /**
     * Add buttons to the LayersPanel that allow the user to add,delete and hide layers.
     *
     * @param drawArea A reference to the programs drawArea.
     */
    private void addLayerButtons(DrawArea drawArea) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        hideShowLayerButton = new JButton(HIDE_SHOW_LAYER_BUTTON_TEXT);
        ActionListener hideShowLayerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listOfLayers.getSelectedIndex() != -1) {
                    ImageLayer selectedLayer = drawArea.getDrawingLayers().get(listOfLayers.getSelectedIndex());
                    selectedLayer.setVisible(!selectedLayer.isVisible());
                    //TODO: add GUI element that shows the layer is hidden
                    listOfLayers.repaint();
                    drawArea.redrawLayers();
                    drawArea.repaint();
                }
            }
        };
        hideShowLayerButton.addActionListener(hideShowLayerActionListener);
        buttonsPanel.add(hideShowLayerButton);

        addLayerButton = new JButton(ADD_LAYER_BUTTON_TEXT);
        ActionListener addLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drawingLayers.size() <= MAX_NUM_OF_LAYERS) {
                    ImageLayer newImageLayer = new ImageLayer(new BufferedImage(
                            drawArea.getWidth(), drawArea.getHeight(), BufferedImage.TYPE_INT_ARGB)
                    );
                    drawingLayers.add(newImageLayer);
                    listModel.addElement(newImageLayer);
                }
            }
        };
        addLayerButton.addActionListener(addLayerButtonActionListener);
        buttonsPanel.add(addLayerButton);

        deleteLayerButton = new JButton(DELETE_LAYER_BUTTON_TEXT);
        ActionListener deleteLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    drawingLayers.remove(selectedIndex);
                    drawArea.redrawLayers();
                }
            }
        };
        deleteLayerButton.addActionListener(deleteLayerButtonActionListener);
        buttonsPanel.add(deleteLayerButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Access the listModel for the list of ImageLayers.
     *
     * @return The listModel for the list of ImageLayers.
     */
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