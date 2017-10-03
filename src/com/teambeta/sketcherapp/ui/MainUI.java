package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.drawingTools.DrawingTool;
import com.teambeta.sketcherapp.drawingTools.LineTool;
import com.teambeta.sketcherapp.drawingTools.BrushTool;
import com.teambeta.sketcherapp.drawingTools.SquareTool;
import com.teambeta.sketcherapp.drawingTools.CircleTool;
import com.teambeta.sketcherapp.drawingTools.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private static LineTool lineTool;
    private static BrushTool brushTool;
    private static SquareTool squareTool;
    private static CircleTool circleTool;
    private static EraserTool eraserTool;
    private static EllipseTool ellipseTool;
    public static DrawingTool selectedDrawingTool;

    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String BRUSH_BUTTON_TEXT = "Brush";
    private static final String SQUARE_TOOL_BUTTON_TEXT = "Square";
    private static final String CIRCLE_TOOL_BUTTON_TEXT = "Circle";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private static final String ERASER_TOOL_BUTTON_TEXT = "Eraser";
    private static final String ELLIPSE_TOOL_BUTTON_TEXT = "Ellipse";
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
    private JButton squareToolButton;
    private JButton circleToolButton;
    private JButton eraserToolButton;
    private JButton ellipseToolButton;

    private WidthChanger widthChanger;

    private DrawArea drawArea;

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawArea.clear();
            } else if (e.getSource() == brushToolButton) {
                widthChanger.showPanel();
                selectedDrawingTool = brushTool;
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == lineToolButton) {
                widthChanger.hidePanel();
                selectedDrawingTool = lineTool;
                drawArea.setColor(lineTool.getColor());
            } else if (e.getSource() == squareToolButton) {
                widthChanger.hidePanel();
                selectedDrawingTool = squareTool;
                drawArea.setColor(squareTool.getColor());
            } else if (e.getSource() == circleToolButton) {
                widthChanger.hidePanel();
                selectedDrawingTool = circleTool;
                drawArea.setColor(circleTool.getColor());
            } else if (e.getSource() == widthChanger.getJTextFieldComponent()) {
                widthChanger.setSliderComponent(widthChanger.getJTextFieldValue());
                widthChanger.setCurrentWidthValue(widthChanger.getJTextFieldValue());
                drawArea.setColor(brushTool.getColor());

            } else if (e.getSource() == ellipseToolButton) {
                widthChanger.hidePanel();
                selectedDrawingTool = ellipseTool;
                drawArea.setColor(ellipseTool.getColor());
            } else if (e.getSource() == eraserToolButton) {
                widthChanger.hidePanel();
                selectedDrawingTool = eraserTool;
                drawArea.setColor(eraserTool.getColor());
            }
        }
    };


    public class listenForSlider implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {

            if (e.getSource() == widthChanger.getSliderComponent()) {
                brushTool.setBrushWidth(widthChanger.getCurrentWidthValue());
                widthChanger.setCurrentWidthValue(brushTool.getBrushWidth());
                widthChanger.setJLabel();
                //   widthChanger.setSizeTextField();
            }


        }

    }

    /**
     * Constructor.
     */
    public MainUI() {
        prepareGUI();
        initDrawingTools();
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

        drawArea = new DrawArea();
        // ideally this should be in its own sliderPanel with a proper scale, not directly to mainContent
        mainContent.add(drawArea, BorderLayout.CENTER);

        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        clearButton.addActionListener(actionListener);
        brushToolButton = new JButton(BRUSH_BUTTON_TEXT);
        brushToolButton.addActionListener(actionListener);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        lineToolButton.addActionListener(actionListener);
        squareToolButton = new JButton(SQUARE_TOOL_BUTTON_TEXT);
        squareToolButton.addActionListener(actionListener);
        circleToolButton = new JButton(CIRCLE_TOOL_BUTTON_TEXT);
        circleToolButton.addActionListener(actionListener);
        eraserToolButton = new JButton(ERASER_TOOL_BUTTON_TEXT);
        eraserToolButton.addActionListener(actionListener);
        ellipseToolButton = new JButton(ELLIPSE_TOOL_BUTTON_TEXT);
        ellipseToolButton.addActionListener(actionListener);

        JPanel canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(clearButton);
        canvasTools.add(brushToolButton);
        canvasTools.add(lineToolButton);
        canvasTools.add(squareToolButton);
        canvasTools.add(circleToolButton);
        canvasTools.add(ellipseToolButton);
        canvasTools.add(eraserToolButton);


        widthChanger = new WidthChanger();
        widthChanger.renderPanel();

        MainUI.listenForSlider listenForSlider = new MainUI.listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);

        mainContent.add(canvasTools, BorderLayout.WEST);
        mainContent.add(widthChanger.getGUI(), BorderLayout.SOUTH);


        ColorChooser colourChooser = new ColorChooser();
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(colourChooser, BorderLayout.NORTH);
        editorPanel.setPreferredSize(new Dimension(EDITOR_PANEL_WIDTH, EDITOR_PANEL_HEIGHT));
        mainContent.add(editorPanel, BorderLayout.EAST);

        prepareMenuBar();
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
        squareTool = new SquareTool();
        circleTool = new CircleTool();

        eraserTool = new EraserTool(drawArea); // Requires drawArea due to requiring the canvas colour.
        ellipseTool = new EllipseTool();
        selectedDrawingTool = brushTool;
    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }


}