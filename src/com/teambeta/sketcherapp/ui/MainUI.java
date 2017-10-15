package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.drawingTools.DrawingTool;
import com.teambeta.sketcherapp.drawingTools.LineTool;
import com.teambeta.sketcherapp.drawingTools.BrushTool;
import com.teambeta.sketcherapp.drawingTools.RectangleTool;
import com.teambeta.sketcherapp.drawingTools.*;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private static LineTool lineTool;
    private static BrushTool brushTool;
    private static RectangleTool rectangleTool;
    private static EraserTool eraserTool;
    private static EllipseTool ellipseTool;
    private static TextTool textTool;
    private static PaintBucketTool paintBucketTool;
    private static EyeDropperTool eyeDropperTool;
    private static FanTool fanTool;
    private static CelticKnotTool celticKnotTool;
    private static DNATool dnaTool;
    public static DrawingTool selectedDrawingTool;


    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String BRUSH_BUTTON_TEXT = "Brush";
    private static final String RECTANGLE_TOOL_BUTTON_TEXT = "Rectangle";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private static final String ERASER_TOOL_BUTTON_TEXT = "Eraser";
    private static final String ELLIPSE_TOOL_BUTTON_TEXT = "Ellipse";
    private static final String TEXT_TOOL_BUTTON_TEXT = "Text";
    private static final String PAINT_BUCKET_BUTTON_TEXT = "Paint Bucket";

    private static final String EYEDROPPER_TOOL_BUTTON_TEXT = "Eye Dropper";
    private static final String FAN_TOOL_BUTTON_TEXT = "Fan-out";
    private static final String CELTIC_KNOT_TOOL_BUTTON_TEXT = "Celtic Knot";
    private static final String DNA_TOOL_BUTTON_TEXT = "DNA";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    public static final int CANVAS_WIDTH = 1920;
    public static final int CANVAS_HEIGHT = 1080;
    private static final int EDITOR_PANEL_WIDTH = 100;
    private static final int EDITOR_PANEL_HEIGHT = 300;

    private static final String FILE_MENU_BUTTON_TEXT = "File";
    private static final String EDIT_MENU_BUTTON_TEXT = "Edit";
    private static final String IMAGE_MENU_BUTTON_TEXT = "Image";
    private static final String WINDOW_MENU_BUTTON_TEXT = "Window";
    private static final String HELP_MENU_BUTTON_TEXT = "Help";

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu imageMenu;
    private JMenu windowMenu;
    private JMenu helpMenu;

    private JButton clearButton;
    private JButton brushToolButton;
    private JButton lineToolButton;
    private JButton rectangleToolButton;
    private JButton eraserToolButton;
    private JButton ellipseToolButton;
    private WidthChanger widthChanger;
    private JButton eyeDropperToolButton;
    private JButton textToolButton;
    private JButton fanToolButton;
    private JButton celticKnotToolButton;
    private JButton paintBucketToolButton;
    private JButton dnaToolButton;
    private DrawArea drawArea;

    private JButton exportButton;


    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawArea.clear();
            } else if (e.getSource() == brushToolButton) {
                widthChanger.showPanel();
                selectedDrawingTool = brushTool;
                updateSizeSlider();
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == fanToolButton) {
                selectedDrawingTool = fanTool;
                updateSizeSlider();
            } else if (e.getSource() == lineToolButton) {
                selectedDrawingTool = lineTool;
                updateSizeSlider();
                drawArea.setColor(lineTool.getColor());
            } else if (e.getSource() == rectangleToolButton) {
                selectedDrawingTool = rectangleTool;
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(rectangleTool.getColor());
            } else if (e.getSource() == widthChanger.getJTextFieldComponent()) {
                widthChanger.setSliderComponent(widthChanger.getJTextFieldValue());
                widthChanger.setCurrentWidthValue(widthChanger.getJTextFieldValue());
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == ellipseToolButton) {
                selectedDrawingTool = ellipseTool;
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(ellipseTool.getColor());
            } else if (e.getSource() == eraserToolButton) {
                selectedDrawingTool = eraserTool;
                updateSizeSlider();
            } else if (e.getSource() == textToolButton) {
                selectedDrawingTool = textTool;
            } else if (e.getSource() == paintBucketToolButton) {
                selectedDrawingTool = paintBucketTool;
                updateSizeSlider();
            } else if (e.getSource() == eyeDropperToolButton) {
                selectedDrawingTool = eyeDropperTool;
            } else if (e.getSource() == celticKnotToolButton) {
                selectedDrawingTool = celticKnotTool;
                updateSizeSlider();
            } else if (e.getSource() == dnaToolButton) {
                selectedDrawingTool = dnaTool;
                updateSizeSlider();
            }

            if (e.getSource() == widthChanger.getCheckBoxGlobalSizeComponent()) {
                if (widthChanger.isGlobalSize()) {
                    widthChanger.setGlobalSize(false);
                } else {
                    widthChanger.setGlobalSize(true);
                }
            }

            if (e.getSource() == widthChanger.getFillBox()) {
                widthChanger.setFill(!widthChanger.isFill());
                selectedDrawingTool.setFillState(widthChanger.isFill());
            }


            if (e.getSource() == exportButton) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("C:\\"));
                fileChooser.setDialogTitle("Save Canvas");
                int retrieval = fileChooser.showSaveDialog(null);

                if (retrieval == JFileChooser.APPROVE_OPTION) {
                    File file = null;
                    //write image to a file
                    try {
                        file = new File(fileChooser.getSelectedFile() + ".png");
                        ImageIO.write(drawArea.getCanvas(), "png", file);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * Constructor.
     */
    public MainUI() {
        prepareGUI();
        initDrawingTools();
    }
    
    /**
     * Class to listen for changes in the widthChanger slider.
     */
    public class listenForSlider implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == widthChanger.getSliderComponent()) {
                selectedDrawingTool.setToolWidth(widthChanger.getCurrentWidthValue());
                widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
                widthChanger.setJLabel();
            }
        }
    }


    /**
     * When a new brushTool is selected this method
     * will update the size panel to the current brush tool
     * values
     * if global is ticked then all the tool sizes change to current size
     * if global not ticked the component size can be different for each tool
     */
    public void updateSizeSlider() {
        if (!widthChanger.isGlobalSize()) {
            widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
            widthChanger.setSliderComponent(selectedDrawingTool.getToolWidth());
            widthChanger.setJLabel();
        } else {
            selectedDrawingTool.setToolWidth(widthChanger.getCurrentWidthValue());
            widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
            widthChanger.setJLabel();
        }
    }

    /**
     * When a new tool is selected, tools that support filling will set their
     * fill state to the fill checkbox.
     */
    public void updateFillState() {
        selectedDrawingTool.setFillState(widthChanger.isFill());
    }

    /**
     * Build main GUI.
     */
    private void prepareGUI() {
        mainFrame = new JFrame(APPLICATION_NAME);
        Container mainContent = mainFrame.getContentPane();
        mainContent.setLayout(new BorderLayout());

        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setLocationByPlatform(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        drawArea = new DrawArea();
        // ideally this should be in its own widthPanel with a proper scale, not directly to mainContent
        mainContent.add(drawArea, BorderLayout.CENTER);

        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        clearButton.addActionListener(actionListener);
        brushToolButton = new JButton(BRUSH_BUTTON_TEXT);
        brushToolButton.addActionListener(actionListener);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        lineToolButton.addActionListener(actionListener);
        rectangleToolButton = new JButton(RECTANGLE_TOOL_BUTTON_TEXT);
        rectangleToolButton.addActionListener(actionListener);
        eraserToolButton = new JButton(ERASER_TOOL_BUTTON_TEXT);
        eraserToolButton.addActionListener(actionListener);
        ellipseToolButton = new JButton(ELLIPSE_TOOL_BUTTON_TEXT);
        ellipseToolButton.addActionListener(actionListener);
        textToolButton = new JButton(TEXT_TOOL_BUTTON_TEXT);
        textToolButton.addActionListener(actionListener);
        eyeDropperToolButton = new JButton(EYEDROPPER_TOOL_BUTTON_TEXT);
        eyeDropperToolButton.addActionListener(actionListener);
        fanToolButton = new JButton(FAN_TOOL_BUTTON_TEXT);
        fanToolButton.addActionListener(actionListener);
        celticKnotToolButton = new JButton(CELTIC_KNOT_TOOL_BUTTON_TEXT);
        celticKnotToolButton.addActionListener(actionListener);
        paintBucketToolButton = new JButton(PAINT_BUCKET_BUTTON_TEXT);
        paintBucketToolButton.addActionListener(actionListener);
        dnaToolButton = new JButton(DNA_TOOL_BUTTON_TEXT);
        dnaToolButton.addActionListener(actionListener);
        exportButton = new JButton("Save");
        exportButton.addActionListener(actionListener);

        JPanel canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(clearButton);
        canvasTools.add(brushToolButton);
        canvasTools.add(lineToolButton);
        canvasTools.add(rectangleToolButton);
        canvasTools.add(ellipseToolButton);
        canvasTools.add(eraserToolButton);
        canvasTools.add(textToolButton);
        canvasTools.add(paintBucketToolButton);

        canvasTools.add(eyeDropperToolButton);
        canvasTools.add(fanToolButton);
        canvasTools.add(celticKnotToolButton);
        canvasTools.add(exportButton);

        widthChanger = new WidthChanger();
        widthChanger.renderPanel();

        MainUI.listenForSlider listenForSlider = new MainUI.listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);
        widthChanger.getCheckBoxGlobalSizeComponent().addActionListener(actionListener);
        widthChanger.getFillBox().addActionListener(actionListener);

        mainContent.add(widthChanger.getGUI(), BorderLayout.NORTH);
        mainContent.add(canvasTools, BorderLayout.WEST);


        ColorChooser colourChooser = new ColorChooser();
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(colourChooser, BorderLayout.NORTH);
        editorPanel.setPreferredSize(new Dimension(EDITOR_PANEL_WIDTH, EDITOR_PANEL_HEIGHT));
        mainContent.add(editorPanel, BorderLayout.EAST);

        prepareMenuBar();
    }

    /**
     * Return the currently selected drawing tool.
     * 
     * @return selected drawing tool.
     */
    public DrawingTool getSelectedDrawingTool() {
        return selectedDrawingTool;
    }

    /**
     * Build GUI menu bar.
     */
    private void prepareMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu(FILE_MENU_BUTTON_TEXT);
        editMenu = new JMenu(EDIT_MENU_BUTTON_TEXT);
        imageMenu = new JMenu(IMAGE_MENU_BUTTON_TEXT);
        windowMenu = new JMenu(WINDOW_MENU_BUTTON_TEXT);
        helpMenu = new JMenu(HELP_MENU_BUTTON_TEXT);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(imageMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Create the drawing tool objects and set the pen tool as the default selection.
     */
    private void initDrawingTools() {
        lineTool = new LineTool();
        brushTool = new BrushTool();
        rectangleTool = new RectangleTool();
        eraserTool = new EraserTool(drawArea); // Requires drawArea due to requiring the canvas colour.
        ellipseTool = new EllipseTool();
        eyeDropperTool = new EyeDropperTool(widthChanger); // Requires widthChanger UI element for direct text update.
        selectedDrawingTool = brushTool;
        textTool = new TextTool();
        fanTool = new FanTool();
        celticKnotTool = new CelticKnotTool();
        paintBucketTool = new PaintBucketTool();
        dnaTool = new DNATool();

    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }
}
