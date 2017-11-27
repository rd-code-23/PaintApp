package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.GeneratorFunctions;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.model.ToolButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class LayersPanel extends JPanel implements ListSelectionListener {
    private static final String HIDE_SHOW_LAYER_BUTTON_TEXT = "Hide/Show";
    private static final String ADD_LAYER_BUTTON_TEXT = "Add";
    private static final String DELETE_LAYER_BUTTON_TEXT = "Delete";
    private static final String RENAME_LAYER_BUTTON_TEXT = "Rename";
    private static final String INPUT_POPUP_TEXT = "Input New Name";
    private static final String DUPLICATE_BUTTON_TEXT = "Duplicate";
    private static final String LAYER_UP_BUTTON_TEXT = "Move Up";
    private static final String LAYER_DOWN_BUTTON_TEXT = "Move Down";
    private static final String LAYER_BG_COLOR = "#4B4B4B";
    private static final String LAYER_SELECT_COLOR = "#909090";
    private static final String FONT_TYPE = "Arial";
    private static final int FONT_SIZE = 18;
    private static final int MAX_NUM_OF_LAYERS = 32;
    private static final int MAX_GROUP_KEY_ID = 10000000;
    private static final int LAYER_OPTIONS_PANEL_PADDING = 30;
    private static final int LAYER_MOVEMENT_BUTTONS_PADDING = 20;
    private static final int LAYER_MOVEMENT_PANEL_WIDTH = 400;
    private static final int LAYER_MOVEMENT_PANEL_HEIGHT = 100;
    private static final int LAYER_PANEL_WIDTH = 383;
    private static final int LAYER_PANEL_HEIGHT = 150;
    private static final long MINIMUM_DUPLICATION_CLEANING_COOLDOWN = 2250;
    private static long lastDuplicationGarbageCollectTime;
    private DrawArea drawArea;
    private LinkedList<ImageLayer> drawingLayers;
    private JList<ImageLayer> listOfLayers = new JList<>();
    private DefaultListModel<ImageLayer> listModel = new DefaultListModel<>();
    private Map<Integer, Integer> duplicationMap = new LinkedHashMap<Integer, Integer>();
    private JScrollPane layersScrollPane;
    private JButton addLayerButton;
    private JButton deleteLayerButton;
    private JButton hideShowLayerButton;
    private JButton renameLayerButton;
    private JButton duplicateLayerButton;
    private JButton layerUpButton;
    private JButton layerDownButton;

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
    private static final String HIDE_LAYER_ICON_DEFAULT = RES_PATH + File.separator + "hide_layer.png";
    private static final String HIDE_LAYER_ICON_HIGHLIGHTED = RES_PATH + File.separator + "hide_layer_highlighted.png";
    private static final String HIDE_LAYER_ICON_HOVER = RES_PATH + File.separator + "hide_layer_hover.png";
    private static final String LAYER_UP_ICON_DEFAULT = RES_PATH + File.separator + "layer_up.png";
    private static final String LAYER_UP_ICON_HIGHLIGHTED = RES_PATH + File.separator + "layer_up_highlighted.png";
    private static final String LAYER_UP_ICON_HOVER = RES_PATH + File.separator + "layer_up_hover.png";
    private static final String LAYER_DOWN_ICON_DEFAULT = RES_PATH + File.separator + "layer_down.png";
    private static final String LAYER_DOWN_ICON_HIGHLIGHTED = RES_PATH + File.separator + "layer_down_highlighted.png";
    private static final String LAYER_DOWN_ICON_HOVER = RES_PATH + File.separator + "layer_down_hover.png";

    /**
     * Return The JList of ImageLayers stored in the LayersPanel
     *
     * @return The stored JList of ImageLayers.
     */
    JList<ImageLayer> getListOfLayers() {
        return listOfLayers;
    }

    /**
     * The constructor for the LayersPanel class.
     *
     * @param drawArea The drawArea the layersPanel is utilizing.
     */
    LayersPanel(DrawArea drawArea) {
        super(new BorderLayout());
        this.drawArea = drawArea;
        this.drawingLayers = drawArea.getDrawingLayers();
        listOfLayers.setFixedCellWidth(LAYER_PANEL_WIDTH);
        listOfLayers.setFixedCellHeight(LAYER_PANEL_HEIGHT);
        listOfLayers.setModel(listModel);
        listOfLayers.setVisibleRowCount(-1);
        listOfLayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfLayers.addListSelectionListener(this);
        listOfLayers.setBackground(Color.decode(LAYER_BG_COLOR));
        listOfLayers.setForeground(Color.WHITE);
        listOfLayers.setSelectionBackground(Color.decode(LAYER_SELECT_COLOR));
        listOfLayers.setSelectionForeground(Color.WHITE);
        listOfLayers.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        listOfLayers.setBorder(null);
        initLayersMouseListener();
        layersScrollPane = new JScrollPane(listOfLayers);
        layersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        layersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        layersScrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
        this.add(layersScrollPane);
        this.add(layersScrollPane, BorderLayout.EAST);
        addLayerButtons(drawArea);
        lastDuplicationGarbageCollectTime = 0;
    }

    /**
     * Implement mouse events for the JList of ImageLayers.
     */
    private void initLayersMouseListener() {
        //derived from: (https://stackoverflow.com/questions/3804361/how-to-enable-drag-and-drop-inside-jlist/18543066)
        //create anon class to implement mouse events.
        MouseAdapter mouseAdapter = new MouseAdapter() {
            int indexOfDraggedLayer;
            boolean isLayerBeingDragged = false;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                isLayerBeingDragged = true;
                indexOfDraggedLayer = listOfLayers.getSelectedIndex();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                isLayerBeingDragged = false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (isLayerBeingDragged) {
                    int indexOurMouseIsAt = listOfLayers.locationToIndex(e.getPoint());
                    if (indexOurMouseIsAt != indexOfDraggedLayer) {
                        //swap layers
                        int targetIndex = listOfLayers.getSelectedIndex();
                        ImageLayer draggedElement = listModel.get(indexOfDraggedLayer);

                        //update listModel after drawingLayers to avoid firing the event handler early.
                        drawingLayers.remove(indexOfDraggedLayer);
                        listModel.remove(indexOfDraggedLayer);
                        drawingLayers.add(targetIndex, draggedElement);
                        listModel.add(targetIndex, draggedElement);
                        indexOfDraggedLayer = indexOurMouseIsAt;
                    }
                }
            }
        };
        listOfLayers.addMouseListener(mouseAdapter);
        listOfLayers.addMouseMotionListener(mouseAdapter);
    }

    /**
     * Add buttons to the LayersPanel that allow the user to add,delete and hide layers.
     *
     * @param drawArea A reference to the programs drawArea.
     */
    private void addLayerButtons(DrawArea drawArea) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        addLayerButton = createButton(ADD_LAYER_ICON_DEFAULT, ADD_LAYER_BUTTON_TEXT);
        addLayerToolMouseListener(addLayerButton, ADD_LAYER_ICON_HIGHLIGHTED, ADD_LAYER_ICON_HOVER,
                ADD_LAYER_ICON_DEFAULT);
        ActionListener addLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Push new layer "above" current layer
                int selectedIndex = listOfLayers.getSelectedIndex();
                // If no layers are selected, push to the top
                if (selectedIndex == -1) {
                    selectedIndex = 0;
                }
                if (drawingLayers.size() < MAX_NUM_OF_LAYERS) {
                    ImageLayer newImageLayer = new ImageLayer(
                            new BufferedImage(
                                    drawArea.getWidth(),
                                    drawArea.getHeight(),
                                    BufferedImage.TYPE_INT_ARGB),
                            null,
                            0
                    );
                    drawingLayers.add(selectedIndex, newImageLayer);
                    listModel.add(selectedIndex, newImageLayer);
                    drawArea.setCurrentlySelectedLayer(newImageLayer);
                    listOfLayers.setSelectedIndex(selectedIndex);
                    listOfLayers.repaint();
                    drawArea.redrawLayers();
                    drawArea.repaint();
                }
            }
        };
        addLayerButton.addActionListener(addLayerButtonActionListener);
        buttonsPanel.add(addLayerButton);

        deleteLayerButton = createButton(DELETE_LAYER_ICON_DEFAULT, DELETE_LAYER_BUTTON_TEXT);
        addLayerToolMouseListener(deleteLayerButton, DELETE_LAYER_ICON_HIGHLIGHTED, DELETE_LAYER_ICON_HOVER,
                DELETE_LAYER_ICON_DEFAULT);
        ActionListener deleteLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    drawingLayers.remove(selectedIndex);
                    if (drawingLayers.isEmpty()) {
                        ImageLayer.resetLayerNumber();
                        duplicationMap.clear();
                    } else {
                        if (listModel.getSize() - selectedIndex > 1) {
                            drawArea.setCurrentlySelectedLayer(listModel.get(selectedIndex));
                            listOfLayers.setSelectedIndex(selectedIndex);
                        } else {
                            drawArea.setCurrentlySelectedLayer(listModel.get(listModel.getSize() - 1));
                            listOfLayers.setSelectedIndex(listModel.getSize() - 1);
                        }

                        listOfLayers.repaint();
                    }

                    // Duplication key "garbage collection"
                    if (System.currentTimeMillis() - lastDuplicationGarbageCollectTime > MINIMUM_DUPLICATION_CLEANING_COOLDOWN) {
                        if (listModel.size() > 0) {
                            ArrayList<Integer> keysToClear = new ArrayList<Integer>();
                            for (Integer duplicationKey : duplicationMap.keySet()) {
                                boolean listModelContainsKey = false;
                                for (int i = 0; i < listModel.size(); ++i) {
                                    if (listModel.getElementAt(i).getLayerDuplicateGroupKey() == (int) duplicationKey) {
                                        listModelContainsKey = true;
                                    }
                                }
                                if (!listModelContainsKey) {
                                    keysToClear.add(duplicationKey);
                                }
                            }
                            for (Integer keyToClear : keysToClear) {
                                duplicationMap.remove(keyToClear);
                            }
                            lastDuplicationGarbageCollectTime = System.currentTimeMillis();
                        }
                    }

                    drawArea.redrawLayers();
                    drawArea.repaint();
                }
            }
        };
        deleteLayerButton.addActionListener(deleteLayerButtonActionListener);
        buttonsPanel.add(deleteLayerButton);

        hideShowLayerButton = createButton(HIDE_LAYER_ICON_DEFAULT, HIDE_SHOW_LAYER_BUTTON_TEXT);
        addLayerToolMouseListener(hideShowLayerButton, HIDE_LAYER_ICON_HIGHLIGHTED, HIDE_LAYER_ICON_HOVER,
                HIDE_LAYER_ICON_DEFAULT);
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

        renameLayerButton = createButton(RENAME_LAYER_ICON_DEFAULT, RENAME_LAYER_BUTTON_TEXT);
        addLayerToolMouseListener(renameLayerButton, RENAME_LAYER_ICON_HIGHLIGHTED, RENAME_LAYER_ICON_HOVER,
                RENAME_LAYER_ICON_DEFAULT);
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
                        // Setup shared duplicate-group key
                            int layerGroupID = GeneratorFunctions.randomInt(0, MAX_GROUP_KEY_ID);
                            // Regenerate key if it is already used
                            while (duplicationMap.containsKey(layerGroupID)) {
                                layerGroupID = GeneratorFunctions.randomInt(0, MAX_GROUP_KEY_ID);
                            }
                            selectedLayer.setLayerDuplicateGroupKey(layerGroupID);
                            duplicationMap.put(layerGroupID, 0);

                            selectedLayer.setName(name);
                            selectedLayer.setDuplicationCount(0);
                            selectedLayer.setOriginalLayerName(name);
                            listOfLayers.repaint();
                            listOfLayers.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
                        }
                    }
                }
            }
        };
        renameLayerButton.addActionListener(renameLayerButtonActionListener);
        buttonsPanel.add(renameLayerButton);

        duplicateLayerButton = createButton(DUPLICATE_LAYER_ICON_DEFAULT, DUPLICATE_BUTTON_TEXT);
        addLayerToolMouseListener(duplicateLayerButton, DUPLICATE_LAYER_ICON_HIGHLIGHTED, DUPLICATE_LAYER_ICON_HOVER,
                DUPLICATE_LAYER_ICON_DEFAULT);
        ActionListener duplicateLayerButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex != -1) {
                    if (drawingLayers.size() < MAX_NUM_OF_LAYERS) {

                        ImageLayer currentlySelectedLayer = drawArea.getCurrentlySelectedLayer();
                        String workingDuplicationName = currentlySelectedLayer.getOriginalLayerName();
                        int layerGroupID;

                        // Setup shared duplicate-group key
                        if (currentlySelectedLayer.getLayerDuplicateGroupKey() == -1) {
                            layerGroupID = GeneratorFunctions.randomInt(0, MAX_GROUP_KEY_ID);
                            // Regenerate key if it is already used
                            while (duplicationMap.containsKey(layerGroupID)) {
                                layerGroupID = GeneratorFunctions.randomInt(0, MAX_GROUP_KEY_ID);
                            }
                            currentlySelectedLayer.setLayerDuplicateGroupKey(layerGroupID);
                        } else {
                            layerGroupID = currentlySelectedLayer.getLayerDuplicateGroupKey();
                        }

                        if (duplicationMap.containsKey(layerGroupID)) {
                            duplicationMap.put(layerGroupID, duplicationMap.get(layerGroupID) + 1);
                        } else {
                            duplicationMap.put(layerGroupID, 1);
                        }

                        ImageLayer newImageLayer = new ImageLayer(
                                new BufferedImage(
                                        drawArea.getWidth(),
                                        drawArea.getHeight(),
                                        BufferedImage.TYPE_INT_ARGB),
                                workingDuplicationName,
                                duplicationMap.get(layerGroupID)
                        );

                        newImageLayer.setLayerDuplicateGroupKey(layerGroupID);
                        Graphics newImageLayerGraphics = newImageLayer.getBufferedImage().getGraphics();
                        newImageLayerGraphics.drawImage(currentlySelectedLayer.getBufferedImage(),
                                0, 0, null);
                        drawingLayers.add(selectedIndex, newImageLayer);
                        listModel.add(selectedIndex, newImageLayer);
                        listOfLayers.setSelectedIndex(selectedIndex);
                        listOfLayers.repaint();
                        drawArea.setCurrentlySelectedLayer(newImageLayer);
                        drawArea.redrawLayers();
                        drawArea.repaint();
                    }
                }
            }
        };
        duplicateLayerButton.addActionListener(duplicateLayerButtonActionListener);
        buttonsPanel.add(duplicateLayerButton);


        JPanel layerMovementButtonsPanel = new JPanel();
        layerMovementButtonsPanel.setLayout(new BoxLayout(layerMovementButtonsPanel, BoxLayout.X_AXIS));
        layerMovementButtonsPanel.setBackground(Color.DARK_GRAY);
        layerMovementButtonsPanel.setBorder(new EmptyBorder(LAYER_MOVEMENT_BUTTONS_PADDING,
                LAYER_MOVEMENT_BUTTONS_PADDING, LAYER_MOVEMENT_BUTTONS_PADDING, LAYER_MOVEMENT_BUTTONS_PADDING));

        layerUpButton = createButton(LAYER_UP_ICON_DEFAULT, LAYER_UP_BUTTON_TEXT);
        layerUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex == -1 || selectedIndex == 0) {
                    //early exit if there is no selected layer or no layer above the selected one.
                } else {
                    ImageLayer imageLayer = listModel.remove(selectedIndex);
                    drawingLayers.remove(selectedIndex);
                    listModel.add(selectedIndex - 1, imageLayer);
                    drawingLayers.add(selectedIndex - 1, imageLayer);
                    listOfLayers.setSelectedIndex(selectedIndex - 1);

                    listOfLayers.repaint();
                    drawArea.redrawLayers();
                }
            }
        });

        layerDownButton = createButton(LAYER_DOWN_ICON_DEFAULT, LAYER_DOWN_BUTTON_TEXT);
        layerDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listOfLayers.getSelectedIndex();
                if (selectedIndex == -1 || selectedIndex == drawingLayers.size() - 1) {
                    //early exit if there is no selected layer or no layer below the selected one.
                } else {
                    ImageLayer imageLayer = listModel.remove(selectedIndex);
                    drawingLayers.remove(selectedIndex);
                    listModel.add(selectedIndex + 1, imageLayer);
                    drawingLayers.add(selectedIndex + 1, imageLayer);
                    listOfLayers.setSelectedIndex(selectedIndex + 1);

                    listOfLayers.repaint();
                    drawArea.redrawLayers();
                }
            }
        });
        addLayerToolMouseListener(layerUpButton, LAYER_UP_ICON_HIGHLIGHTED, LAYER_UP_ICON_HOVER, LAYER_UP_ICON_DEFAULT);
        addLayerToolMouseListener(layerDownButton, LAYER_DOWN_ICON_HIGHLIGHTED, LAYER_DOWN_ICON_HOVER,
                LAYER_DOWN_ICON_DEFAULT);
        layerMovementButtonsPanel.add(layerUpButton);
        layerMovementButtonsPanel.add(layerDownButton);

        JPanel layerMovementPanel = new JPanel();
        layerMovementPanel.setLayout(new BoxLayout(layerMovementPanel, BoxLayout.Y_AXIS));
        layerMovementPanel.setBackground(Color.DARK_GRAY);
        layerMovementPanel.add(layerMovementButtonsPanel);
        layerMovementPanel.setMaximumSize(new Dimension(LAYER_MOVEMENT_PANEL_WIDTH, LAYER_MOVEMENT_PANEL_HEIGHT));
        layerMovementPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JPanel layerOptionsPanel = new JPanel();
        layerOptionsPanel.setLayout(new BoxLayout(layerOptionsPanel, BoxLayout.Y_AXIS));
        layerOptionsPanel.setBorder(new EmptyBorder(LAYER_OPTIONS_PANEL_PADDING,
                LAYER_OPTIONS_PANEL_PADDING, LAYER_OPTIONS_PANEL_PADDING, LAYER_OPTIONS_PANEL_PADDING));
        layerOptionsPanel.setBackground(Color.DARK_GRAY);
        layerOptionsPanel.add(layerMovementPanel);
        layerOptionsPanel.add(Box.createRigidArea(new Dimension(0, LAYER_OPTIONS_PANEL_PADDING)));
        layerOptionsPanel.add(buttonsPanel);

        this.add(layerOptionsPanel, BorderLayout.SOUTH);
    }

    /**
     * Add mouse listener for mouse over and selection effects to layer buttons.
     *
     * @param button              layer tool button.
     * @param iconHighlightedPath path to highlighted icon.
     * @param iconHoverPath       path to hover icon.
     * @param iconDefaultPath     path to icon.
     */
    private void addLayerToolMouseListener(JButton button, String iconHighlightedPath, String iconHoverPath,
                                           String iconDefaultPath) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                button.setIcon(new ImageIcon(iconHighlightedPath));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Point mousePoint = new java.awt.Point(e.getLocationOnScreen());
                SwingUtilities.convertPointFromScreen(mousePoint, e.getComponent());
                if (e.getComponent().contains(mousePoint)) {
                    button.setIcon(new ImageIcon(iconHoverPath));
                } else {
                    button.setIcon(new ImageIcon(iconDefaultPath));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setIcon(new ImageIcon(iconHoverPath));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setIcon(new ImageIcon(iconDefaultPath));
            }
        });
    }

    /**
     * Create a JButton with a specific tooltip format.
     *
     * @param iconPath    path to the button's icon.
     * @param toolTipText text for the tool tip on mouse over.
     * @return created JButton.
     */
    private JButton createButton(String iconPath, String toolTipText) {
        JButton button = new ToolButton(new ImageIcon(iconPath));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setToolTipText(toolTipText);
        return button;
    }

    /**
     * Access the listModel for the list of ImageLayers.
     *
     * @return The listModel for the list of ImageLayers.
     */
    DefaultListModel<ImageLayer> getListModel() {
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