package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.ImageLayer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

public class LayersPanel extends JPanel implements ListSelectionListener {
    private static final String HIDE_SHOW_LAYER_BUTTON_TEXT = "Hide/Show";
    private static final String ADD_LAYER_BUTTON_TEXT = "Add";
    private static final String DELETE_LAYER_BUTTON_TEXT = "Delete";
    private static final String RENAME_LAYER_BUTTON_TEXT = "Rename";
    private static final String INPUT_POPUP_TEXT = "Input New Name";
    private static final String DUPLICATE_BUTTON_TEXT = "Duplicate";
    private static final int MAX_NUM_OF_LAYERS = 32;
    private DrawArea drawArea;
    private LinkedList<ImageLayer> drawingLayers;
    private JList<ImageLayer> listOfLayers = new JList<>();
    private DefaultListModel<ImageLayer> listModel = new DefaultListModel<>();
    private JScrollPane layersScrollPane;
    private JButton hideShowLayerButton;
    private JButton addLayerButton;
    private JButton deleteLayerButton;
    private JButton renameLayerButton;
    private JButton duplicateLayerButton;

    private static final String RES_PATH = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res";
    private static final String ADD_LAYER_ICON_DEFAULT = RES_PATH + File.separator + "layer.png";
    private static final String ADD_LAYER_ICON_HIGHLIGHTED = RES_PATH + File.separator + "layer_highlighted.png";
    private static final String ADD_LAYER_ICON_HOVER = RES_PATH + File.separator + "layer_hover.png";
    private static final String DELETE_LAYER_ICON_DEFAULT = RES_PATH + File.separator + "delete_layer.png";
    private static final String DELETE_LAYER_ICON_HIGHLIGHTED = RES_PATH + File.separator +
            "delete_layer_highlighted.png";
    private static final String DELETE_LAYER_ICON_HOVER = RES_PATH + File.separator + "delete_layer_hover.png";
    private static final String RENAME_LAYER_ICON_DEFAULT = RES_PATH + File.separator + "rename_layer.png";
    private static final String RENAME_LAYER_ICON_HIGHLIGHTED = RES_PATH + File.separator +
            "rename_layer_highlighted.png";
    private static final String RENAME_LAYER_ICON_HOVER = RES_PATH + File.separator + "rename_layer_hover.png";
    private static final String DUPLICATE_LAYER_ICON_DEFAULT = RES_PATH + File.separator + "duplicate_layer.png";
    private static final String DUPLICATE_LAYER_ICON_HIGHLIGHTED = RES_PATH + File.separator +
            "duplicate_layer_highlighted.png";
    private static final String DUPLICATE_LAYER_ICON_HOVER = RES_PATH + File.separator + "duplicate_layer_hover.png";

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
        this.add(layersScrollPane);
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
        buttonsPanel.setBackground(Color.DARK_GRAY);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

        hideShowLayerButton = new JButton(HIDE_SHOW_LAYER_BUTTON_TEXT);
        ActionListener hideShowLayerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listOfLayers.getSelectedIndex() != -1) {
                    ImageLayer selectedLayer = drawArea.getDrawingLayers().get(listOfLayers.getSelectedIndex());
                    selectedLayer.setVisible(!selectedLayer.isVisible());
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
                if (drawingLayers.size() < MAX_NUM_OF_LAYERS) {
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

        renameLayerButton = new JButton(RENAME_LAYER_BUTTON_TEXT);
        ActionListener renameLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex != -1) {
                    //get the new name
                    String name = JOptionPane.showInputDialog(INPUT_POPUP_TEXT);
                    if (name != null) {
                        ImageLayer selectedLayer = drawArea.getDrawingLayers().get(selectedIndex);
                        if (selectedLayer != null) {
                            selectedLayer.setName(name);
                            listOfLayers.repaint();
                        }
                    }
                }
            }
        };
        renameLayerButton.addActionListener(renameLayerButtonActionListener);
        buttonsPanel.add(renameLayerButton);

        duplicateLayerButton = new JButton(DUPLICATE_BUTTON_TEXT);
        ActionListener duplicateLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listOfLayers.getSelectedIndex() != -1) {
                    if (drawingLayers.size() < MAX_NUM_OF_LAYERS) {
                        ImageLayer newImageLayer = new ImageLayer(new BufferedImage(
                                drawArea.getWidth(),
                                drawArea.getHeight(),
                                BufferedImage.TYPE_INT_ARGB)
                        );
                        ImageLayer currentlySelectedLayer = drawArea.getCurrentlySelectedLayer();
                        Graphics newImageLayerGraphics = newImageLayer.getBufferedImage().getGraphics();
                        newImageLayerGraphics.drawImage(currentlySelectedLayer.getBufferedImage(),
                                0, 0, null);
                        drawingLayers.add(newImageLayer);
                        listModel.addElement(newImageLayer);
                        listOfLayers.repaint();
                        drawArea.redrawLayers();
                        drawArea.repaint();
                    }
                }
            }
        };
        duplicateLayerButton.addActionListener(duplicateLayerButtonActionListener);
        buttonsPanel.add(duplicateLayerButton);

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
            drawArea.redrawLayers();
        }
        drawArea.repaint();
        listOfLayers.repaint();
    }
}