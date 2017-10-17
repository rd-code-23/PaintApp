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
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
    private static AirBrushTool airBrushTool;
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
    private static final String AIR_BRUSH_TOOL_BUTTON_TEXT = "Airbrush";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    private static final int EDITOR_PANEL_WIDTH = 100;
    private static final int EDITOR_PANEL_HEIGHT = 300;
    public static final int CANVAS_WIDTH = 1920;
    public static final int CANVAS_HEIGHT = 1080;


    private JButton clearButton;
    private JButton brushToolButton;
    private JButton lineToolButton;
    private JButton rectangleToolButton;
    private JButton eraserToolButton;
    private JButton ellipseToolButton;
    private JButton eyeDropperToolButton;
    private JButton textToolButton;
    private JButton fanToolButton;
    private JButton celticKnotToolButton;
    private JButton paintBucketToolButton;
    private JButton dnaToolButton;
    private JButton airBrushToolButton;
    private WidthChanger widthChanger;
    private DrawArea drawArea;

    //adding key binding stuff


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
            } else if (e.getSource() == airBrushToolButton) {
                selectedDrawingTool = airBrushTool;
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


    public  JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
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

        /* START MAINUI BUTTONS */

        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        brushToolButton = new JButton(BRUSH_BUTTON_TEXT);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        rectangleToolButton = new JButton(RECTANGLE_TOOL_BUTTON_TEXT);
        eraserToolButton = new JButton(ERASER_TOOL_BUTTON_TEXT);
        ellipseToolButton = new JButton(ELLIPSE_TOOL_BUTTON_TEXT);
        textToolButton = new JButton(TEXT_TOOL_BUTTON_TEXT);
        eyeDropperToolButton = new JButton(EYEDROPPER_TOOL_BUTTON_TEXT);
        fanToolButton = new JButton(FAN_TOOL_BUTTON_TEXT);
        celticKnotToolButton = new JButton(CELTIC_KNOT_TOOL_BUTTON_TEXT);
        paintBucketToolButton = new JButton(PAINT_BUCKET_BUTTON_TEXT);
        dnaToolButton = new JButton(DNA_TOOL_BUTTON_TEXT);
        airBrushToolButton = new JButton(AIR_BRUSH_TOOL_BUTTON_TEXT);

        // Add a button to this array to register to actionListener and canvasTools
        JButton[] buttonContainer = {clearButton, brushToolButton, lineToolButton, rectangleToolButton,
                ellipseToolButton, eraserToolButton, textToolButton, paintBucketToolButton, fanToolButton,
                celticKnotToolButton, dnaToolButton, eyeDropperToolButton, airBrushToolButton
                 };

        JPanel canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);

        // Register buttons to actionListener and canvasTools
        for (JButton button : buttonContainer) {
            button.addActionListener(actionListener);
            canvasTools.add(button);
        }

        /* END MAINUI BUTTONS */

        JPanel northPanels = new JPanel();
        northPanels.setLayout(new BorderLayout());
        MenuUI menuUI = new MenuUI(drawArea,this);
        northPanels.add(menuUI, BorderLayout.NORTH);

        widthChanger = new WidthChanger();
        widthChanger.renderPanel();
        northPanels.add(widthChanger.getGUI(), BorderLayout.CENTER);

        MainUI.listenForSlider listenForSlider = new MainUI.listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);
        widthChanger.getCheckBoxGlobalSizeComponent().addActionListener(actionListener);
        widthChanger.getFillBox().addActionListener(actionListener);



        mainContent.add(canvasTools, BorderLayout.WEST);

        ColorChooser colourChooser = new ColorChooser();
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(colourChooser, BorderLayout.NORTH);
        editorPanel.setPreferredSize(new Dimension(EDITOR_PANEL_WIDTH, EDITOR_PANEL_HEIGHT));
        mainContent.add(editorPanel, BorderLayout.EAST);

        mainContent.add(northPanels, BorderLayout.NORTH);



        //adding key binding
            generateDefaultKeyBindings(editorPanel);

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
     * Create the drawing tool objects and set the pen tool as the default selection.
     */
    private void initDrawingTools() {
        lineTool = new LineTool();
        brushTool = new BrushTool();
        rectangleTool = new RectangleTool();
        eraserTool = new EraserTool(drawArea); // Requires drawArea due to requiring the canvas colour.
        ellipseTool = new EllipseTool();
        eyeDropperTool = new EyeDropperTool(widthChanger); // Requires widthChanger UI element for direct text update.
        textTool = new TextTool();
        fanTool = new FanTool();
        celticKnotTool = new CelticKnotTool();
        paintBucketTool = new PaintBucketTool();
        dnaTool = new DNATool();
        airBrushTool = new AirBrushTool();
        selectedDrawingTool = brushTool;
    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }


    public void generateDefaultKeyBindings(JPanel editorPanel) {


        //TODO cannot get clear to work with ctrl+c ??? thats why i added the boolean to function call
        addKeyBinding(editorPanel, KeyEvent.VK_C, false, "CLEAR CANVAS", (evt) -> {
            drawArea.clear();
        });


        addKeyBinding(editorPanel, KeyEvent.VK_B, true, "BRUSH TOOL", (evt) -> {
            selectedDrawingTool = brushTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_L, true, "LINE TOOL", (evt) -> {
            selectedDrawingTool = lineTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_R, true, "RECT TOOL", (evt) -> {
            selectedDrawingTool = rectangleTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_E, true, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_Q, true, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_E, true, "ERASER TOOL", (evt) -> {
            selectedDrawingTool = eraserTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_T, true, "TEXT TOOL", (evt) -> {
            selectedDrawingTool = textTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_P, true, "PAINTBUCKET TOOL", (evt) -> {
            selectedDrawingTool = paintBucketTool;
        });

        addKeyBinding(editorPanel, KeyEvent.VK_F, true, "PAINTBUCKET TOOL", (evt) -> {
            selectedDrawingTool = fanTool;
        });

     //   addKeyBinding(editorPanel, KeyEvent.VK_S, true, "PAINTBUCKET TOOL", (evt) -> {
      //     ME;
      //  });
    }

    public static void addKeyBinding(JComponent component, int keyCode,boolean useControl, String id,ActionListener actionListener ){

        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        if(useControl) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK), id);
        } else {
            im.put(KeyStroke.getKeyStroke(keyCode, 0,false),id);
        }

        ActionMap actionMap = component.getActionMap();

        actionMap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });



    }

}
