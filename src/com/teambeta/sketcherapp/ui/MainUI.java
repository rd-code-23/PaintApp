package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.DrawingTool;
import com.teambeta.sketcherapp.model.LineTool;
import com.teambeta.sketcherapp.model.PenTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    public static LineTool lineTool;
    public static PenTool penTool;
    public static DrawingTool selectedDrawingTool;

    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String PEN_BUTTON_TEXT = "Pen";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    public static final int CANVAS_WIDTH = 1920;
    public static final int CANVAS_HEIGHT = 1080;

    private JButton clearButton;
    private JButton penToolButton;
    private JButton lineToolButton;
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
        // ideally this should be in its own panel with a proper scale, not directly to mainContent
        mainContent.add(drawArea, BorderLayout.CENTER);

        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        clearButton.addActionListener(actionListener);
        penToolButton = new JButton(PEN_BUTTON_TEXT);
        penToolButton.addActionListener(actionListener);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        lineToolButton.addActionListener(actionListener);

        JPanel canvasTools = new JPanel();
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(clearButton);
        canvasTools.add(penToolButton);
        canvasTools.add(lineToolButton);

        mainContent.add(canvasTools, BorderLayout.WEST);
    }

    private void initDrawingTools() {
        lineTool = new LineTool();
        penTool = new PenTool();
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
