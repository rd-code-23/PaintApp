package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.drawingTools.DrawingTool;
import com.teambeta.sketcherapp.drawingTools.LineTool;
import com.teambeta.sketcherapp.drawingTools.PenTool;
import com.teambeta.sketcherapp.drawingTools.SquareTool;
import com.teambeta.sketcherapp.drawingTools.CircleTool;

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
    private static PenTool penTool;
    private static SquareTool squareTool;
    private static CircleTool circleTool;
    public static DrawingTool selectedDrawingTool;

    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String PEN_BUTTON_TEXT = "Pen";
    private static final String SQUARE_TOOL_BUTTON_TEXT = "Square";
    private static final String CIRCLE_TOOL_BUTTON_TEXT = "Circle";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    public static final int CANVAS_WIDTH = 1920;
    public static final int CANVAS_HEIGHT = 1080;

    private static final String FILE_MENU_BUTTON_TEXT = "File";
    private static final String EDIT_MENU_BUTTON_TEXT = "Edit";
    private static final String IMAGE_MENU_BUTTON_TEXT = "Image";
    private static final String WINDOW_MENU_BUTTON_TEXT = "Window";
    private static final String HELP_MENU_BUTTON_TEXT = "Help";

    private static final int INITIAL_SLIDER_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMIM_SLIDER_VALUE = 100;
    private static final int SIZE_FONT_JLABEL = 24;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu imageMenu;
    private JMenu windowMenu;
    private JMenu helpMenu;

    private JButton clearButton;
    private JButton penToolButton;
    private JButton lineToolButton;
    private JButton squareToolButton;
    private JButton circleToolButton;

    private JSlider penWidthSlider;
    private JLabel penWidthSliderLabel;

    private DrawArea drawArea;

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawArea.clear();
            } else if (e.getSource() == penToolButton) {
                selectedDrawingTool = penTool;
                drawArea.setColor(penTool.getColor());
            } else if (e.getSource() == lineToolButton) {
                selectedDrawingTool = lineTool;
                drawArea.setColor(lineTool.getColor());
            } else if (e.getSource() == squareToolButton) {
                selectedDrawingTool = squareTool;
                drawArea.setColor(squareTool.getColor());
            } else if (e.getSource() == circleToolButton) {
                selectedDrawingTool = circleTool;
                drawArea.setColor(circleTool.getColor());
            }
        }
    };


    public class listenForSlider implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {

            if (e.getSource() == penWidthSlider) {
                penTool.setPenWidth(penWidthSlider.getValue());
                penWidthSliderLabel.setText("SIZE: " + penWidthSlider.getValue());
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
        penToolButton = new JButton(PEN_BUTTON_TEXT);
        penToolButton.addActionListener(actionListener);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        lineToolButton.addActionListener(actionListener);
        squareToolButton = new JButton(SQUARE_TOOL_BUTTON_TEXT);
        squareToolButton.addActionListener(actionListener);
        circleToolButton = new JButton(CIRCLE_TOOL_BUTTON_TEXT);
        circleToolButton.addActionListener(actionListener);

        JPanel canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(clearButton);
        canvasTools.add(penToolButton);
        canvasTools.add(lineToolButton);
        canvasTools.add(squareToolButton);
        canvasTools.add(circleToolButton);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        sliderPanel.setBackground(Color.DARK_GRAY);

        penWidthSliderLabel = new JLabel("SIZE: " + INITIAL_SLIDER_VALUE);
        penWidthSliderLabel.setForeground(Color.red);
        penWidthSliderLabel.setFont(new Font("Serif", Font.PLAIN, SIZE_FONT_JLABEL));

        penWidthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMIM_SLIDER_VALUE, INITIAL_SLIDER_VALUE);
        penWidthSlider.setMinorTickSpacing(5);
        penWidthSlider.setPaintLabels(true);
        penWidthSlider.setMajorTickSpacing(20);
        penWidthSlider.setPaintTicks(true);
        listenForSlider listenForSlider = new listenForSlider();
        penWidthSlider.addChangeListener(listenForSlider);

        sliderPanel.add(penWidthSliderLabel);
        sliderPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        sliderPanel.add(penWidthSlider);

        mainContent.add(canvasTools, BorderLayout.WEST);
        mainContent.add(sliderPanel, BorderLayout.SOUTH);

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
        penTool = new PenTool();
        squareTool = new SquareTool();
        circleTool = new CircleTool();
        selectedDrawingTool = penTool;
    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }
}