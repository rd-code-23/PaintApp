package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.Shortcuts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.*;


public class ShortcutDialog {

    private static final int SHORTCUT_DIALOG_WIDTH = 700;
    private static final int SHORTCUT_DIALOG_HEIGHT = 900;
    private static final String KEYBOARD_SHORTCUTS_TITLE = "Keyboard Shortcuts";
    private static final String RE_ASSIGN_BUTTON_TABLE_COL = "Re-Assign";
    private static final String RE_ASSIGN_SHORTCUT_TABLE_COL = RE_ASSIGN_BUTTON_TABLE_COL;
    private static final String CLEAR_SHORTCUT_TABLE = "Clear";
    private static final String BRUSH_SHORTCUT_TABLE = "Brush";
    private static final String LINE_SHORTCUT_TABLE = "Line";
    private static final String IMPORT_SHORTCUT_TABLE = "Import";
    private static final String EXPORT_SHORTCUT_TABLE = "Export";
    private static final String AIRBRUSH_SHORTCUT_TABLE = "Air Brush";
    private static final String ERASER_SHORTCUT_TABLE = "Eraser";
    private static final String CELTICKNOT_SHORTCUT_TABLE = "Celtic Knot";
    private static final String COLOR_CHOOSER_SHORTCUT_TABLE = "Color Chooser";
    private static final String DNA_SHORTCUT_TABLE = "DNA";
    private static final String ELLIPSE_SHORTCUT_TABLE = "Ellipse";
    private static final String EYEDROP_SHORTCUT_TABLE = "Eye Drop";
    private static final String FAN_SHORTCUT_TABLE = "Fan";
    private static final String PAINTBUCKET_SHORTCUT_TABLE = "Paint Bucket";
    private static final String SELECTION_SHORTCUT_TABLE = "Selection";
    private static final String RECTANGLE_SHORTCUT_TABLE = "Rectangle";
    private static final String TEXT_SHORTCUT_TABLE = "Text";
    private static final String TRIANGLE_SHORTCUT_TABLE = "Triangle";
    private static final String PRINT_SHORTCUT_TABLE = "Print";

    private static final String ACTION_SHORTCUT_TABLE_COL = "Action";
    private static final String DEFAULT_BUTTON = "Default";
    private static final String APPLY_BUTTON = "Apply";
    private static final String CTRL_PLUS_STRING = "Ctrl+";
    private static final String SHIFT_PLUS_STRING = "Shift+";
    private static final String ALT_PLUS_STRING = "Alt+";

    private MainUI mainUI;
    private Shortcuts sc;
    private JButton setDefaultShortcutsButton;
    private JButton applyButton;
    private JDialog shortcutsDialog;

    public ShortcutDialog(MainUI mainUI, Shortcuts shortcuts) {
        this.mainUI = mainUI;
        this.sc = shortcuts;
    }

    // This class was taken and modified from  http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
    // Also got help from https://stackoverflow.com/questions/46985936/using-keybinding-and-action-map-in-java-for-shortcut-keys-for-buttons

    /**
     * renders the panel for the shortcut dialog
     */
    public void renderPanel() {

        DefaultTableModel dm = new DefaultTableModel();

        dm.setDataVector(new Object[][]{
                        {CLEAR_SHORTCUT_TABLE, printShortcut(sc.getClearToolKeyCode(), sc.isCtrl_clearTool(), sc.isShift_clearTool(), sc.isAlt_clearTool())},
                        {BRUSH_SHORTCUT_TABLE, printShortcut(sc.getBrushToolKeyCode(), sc.isCtrl_brushTool(), sc.isShift_brushTool(), sc.isAlt_brushTool())},
                        {AIRBRUSH_SHORTCUT_TABLE, printShortcut(sc.getAirBrushToolKeyCode(), sc.isCtrl_airBrushTool(), sc.isShift_airBrushTool(), sc.isAlt_airBrushTool())},
                        {ERASER_SHORTCUT_TABLE, printShortcut(sc.getEraserToolKeyCode(), sc.isCtrl_eraserTool(), sc.isShift_eraserTool(), sc.isAlt_eraserTool())},
                        {LINE_SHORTCUT_TABLE, printShortcut(sc.getLineToolKeyCode(), sc.isCtrl_lineTool(), sc.isShift_lineTool(), sc.isAlt_lineTool())},
                        {FAN_SHORTCUT_TABLE, printShortcut(sc.getFanToolKeyCode(), sc.isCtrl_fanTool(), sc.isShift_fanTool(), sc.isAlt_fanTool())},
                        {RECTANGLE_SHORTCUT_TABLE, printShortcut(sc.getRectToolKeyCode(), sc.isCtrl_rectTool(), sc.isShift_rectTool(), sc.isAlt_rectTool())},
                        {ELLIPSE_SHORTCUT_TABLE, printShortcut(sc.getEllipseToolKeyCode(), sc.isCtrl_ellipseTool(), sc.isShift_ellipseTool(), sc.isAlt_ellipseTool())},
                        {TRIANGLE_SHORTCUT_TABLE, printShortcut(sc.getTriangleToolKeyCode(), sc.isCtrl_triangleTool(), sc.isShift_triangleTool(), sc.isAlt_triangleTool())},
                        {PAINTBUCKET_SHORTCUT_TABLE, printShortcut(sc.getPaintBucketToolKeyCode(), sc.isCtrl_paintBucketTool(), sc.isShift_paintBucketTool(), sc.isAlt_paintBucketTool())},
                        {CELTICKNOT_SHORTCUT_TABLE, printShortcut(sc.getCelticKnotToolKeyCode(), sc.isCtrl_celticKnotTool(), sc.isShift_celticKnotTool(), sc.isAlt_celticKnotTool())},
                        {DNA_SHORTCUT_TABLE, printShortcut(sc.getDnaToolKeyCode(), sc.isCtrl_dnaTool(), sc.isShift_dnaTool(), sc.isAlt_dnaTool())},
                        {TEXT_SHORTCUT_TABLE, printShortcut(sc.getTextToolKeyCode(), sc.isCtrl_textTool(), sc.isShift_textTool(), sc.isAlt_textTool())},
                        {COLOR_CHOOSER_SHORTCUT_TABLE, printShortcut(sc.getColorChooserToolKeyCode(), sc.isCtrl_colorChooserTool(), sc.isShift_colorChooserTool(), sc.isAlt_colorChooserTool())},
                        {EYEDROP_SHORTCUT_TABLE, printShortcut(sc.getEyeDropToolKeyCode(), sc.isCtrl_eyeDropTool(), sc.isShift_eyeDropTool(), sc.isAlt_eyeDropTool())},
                        {SELECTION_SHORTCUT_TABLE, printShortcut(sc.getSelectionToolKeyCode(), sc.isCtrl_selectionTool(), sc.isShift_selectionTool(), sc.isAlt_selectionTool())},
                        {IMPORT_SHORTCUT_TABLE, printShortcut(sc.getImportKeyCode(), sc.isCtrl_import(), sc.isShift_import(), sc.isAlt_import())},
                        {EXPORT_SHORTCUT_TABLE, printShortcut(sc.getExportKeyCode(), sc.isCtrl_export(), sc.isShift_export(), sc.isAlt_export())},
                        {PRINT_SHORTCUT_TABLE, printShortcut(sc.getPrintToolKeyCode(), sc.isCtrl_printTool(), sc.isShift_printTool(), sc.isAlt_printTool())},
                },
                new Object[]{ACTION_SHORTCUT_TABLE_COL, RE_ASSIGN_SHORTCUT_TABLE_COL});

        JTable table = new JTable(dm);
        setColumnHeight(table);

        table.getColumn(RE_ASSIGN_SHORTCUT_TABLE_COL).setCellRenderer(new ButtonRenderer());
        table.getColumn(RE_ASSIGN_SHORTCUT_TABLE_COL).setCellEditor(
                new ButtonEditor(new JCheckBox()));
        JScrollPane scroll = new JScrollPane(table);

        shortcutsDialog = new JDialog(mainUI.getMainFrame(), KEYBOARD_SHORTCUTS_TITLE, true);
        shortcutsDialog.setLocationRelativeTo(null);
        shortcutsDialog.setSize(SHORTCUT_DIALOG_WIDTH, SHORTCUT_DIALOG_HEIGHT);
        shortcutsDialog.setLayout(new GridBagLayout());

        setDefaultShortcutsButton = new JButton(DEFAULT_BUTTON);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setDefaultShortcutsButton) {
                    sc.resetDefaultBindings();
                    shortcutsDialog.dispose();
                    renderPanel();
                }
            }
        };

        applyButton = new JButton(APPLY_BUTTON);
        ActionListener actionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == applyButton) {
                    shortcutsDialog.dispose();
                }
            }
        };

        setDefaultShortcutsButton.addActionListener(actionListener);
        applyButton.addActionListener(actionListener2);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        shortcutsDialog.add(scroll, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = -1;
        c.gridy = 3;
        shortcutsDialog.add(setDefaultShortcutsButton, c);
        c.gridx = -1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        //  shortcutsDialog.add(Box.createRigidArea(new Dimension(0, 0)),c);
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        shortcutsDialog.add(applyButton, c);

        shortcutsDialog.setVisible(true);

    }

    /**
     * prints the binding for a shortcut
     */
    String printShortcut(int keyCode, boolean useControl, boolean useShift, boolean useAlt) {

        String string = "";

        if (useControl) {
            string += CTRL_PLUS_STRING;
        }

        if (useShift) {
            string += SHIFT_PLUS_STRING;
        }

        if (useAlt) {
            string += ALT_PLUS_STRING;
        }

        string += (char) keyCode;

        return string;
    }

    /**
     * renders the button
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
           // setFont(new Font("David", Font.PLAIN, 16));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     * class is used to change the shortcut keys
     */
    class ButtonEditor extends DefaultCellEditor {
        private static final String EDIT_SHORTCUT_TITLE_EDIT_SHORTCUT_DIALOG = "Edit Shortcut";
        private static final int WIDTH_EDIT_SHORTCUT_DIALOG = 450;
        private static final int HEIGHT_EDIT_SHORTCUT_DIALOG = 200;
        private static final String KEY_STROKE_SYMBOL_BUTTON = "-";
        private static final String SAVE_TEXT_BUTTON = "Save";
        private static final String CANCEL_TEXT_BUTTON = "Cancel";
        private static final String CTRL_TEXT_BUTTON = "ctrl";
        private static final String ALT_TEXT_BUTTON = "alt";
        private static final String SHIFT_TEXT_BUTTON = "shift";
        private static final String INSTRUCTION_EDIT_SHORTCUT_LABEL = "Toggle ctrl,alt and shift. Then type a letter";
        private static final String INSTRUCTION_INVALID_BINDING_EDIT_LABEL = "That Binding is Already Taken! Try Again";
        private static final String INSTRUCTION_ENTER_LETTER_LABEL = "You need to enter a letter!";
        private static final String NO_SUCH_TOOL = "NO SUCH TOOL";

        protected JButton button;
        private int newKeyCode = -1;
        private JToggleButton ctrlKey;
        private JToggleButton altKey;
        private JToggleButton shiftKey;
        private JButton strokeKey;
        private JButton saveButton;
        private JButton exitButton;
        private KeyStroke ks;
        private JDialog dialog;
        private JLabel instructions;
        private JLabel notValidShortcut;
        private String label;
        private int ROW_SHORTCUT_TABLE;
        private boolean isPushed;

        /**
         * used to edit the button
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        /**
         * get the displayed value on the button in the shortcut dialog
         */
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            ROW_SHORTCUT_TABLE = row;

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            button.setText(label);

            button.setFont(new Font("David", Font.PLAIN, 16));
            isPushed = true;

            return button;
        }

        /**
         * get the user new key binding and return it to the shortcut dialog
         */
        public Object getCellEditorValue() {
            dialog = new JDialog(mainUI.getMainFrame(), EDIT_SHORTCUT_TITLE_EDIT_SHORTCUT_DIALOG, true);

            JPanel listPane;
            listPane = new JPanel();

            listPane.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {

                    int code = e.getKeyCode();
                    if (code != KeyEvent.VK_CONTROL && code != KeyEvent.VK_ALT && code != KeyEvent.VK_SHIFT && code != KeyEvent.VK_META && code != KeyEvent.VK_CAPS_LOCK) {
                        strokeKey.setText(KeyEvent.getKeyText(code));
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    ks = KeyStroke.getKeyStroke(Character.toUpperCase(e.getKeyChar()), 0);
                    newKeyCode = ks.getKeyCode();
                }
            });

            strokeKey = new JButton(KEY_STROKE_SYMBOL_BUTTON);
            saveButton = new JButton(SAVE_TEXT_BUTTON);
            exitButton = new JButton(CANCEL_TEXT_BUTTON);
            ctrlKey = new JToggleButton(CTRL_TEXT_BUTTON);
            altKey = new JToggleButton(ALT_TEXT_BUTTON);
            shiftKey = new JToggleButton(SHIFT_TEXT_BUTTON);
            instructions = new JLabel(INSTRUCTION_EDIT_SHORTCUT_LABEL);
            notValidShortcut = new JLabel();
            GridBagConstraints c = new GridBagConstraints();
            ctrlKey.setFocusable(false);
            shiftKey.setFocusable(false);
            altKey.setFocusable(false);
            saveButton.setFocusable(false);
            exitButton.setFocusable(false);
            strokeKey.setFocusable(false);

            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    boolean isCtrl = ctrlKey.isSelected();
                    boolean isAlt = altKey.isSelected();
                    boolean isShift = shiftKey.isSelected();
                    if (!sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        notValidShortcut.setForeground(Color.red);
                        notValidShortcut.setOpaque(true);
                        notValidShortcut.setText(INSTRUCTION_INVALID_BINDING_EDIT_LABEL);

                    } else if (newKeyCode == -1) {
                        notValidShortcut.setForeground(Color.red);
                        notValidShortcut.setOpaque(true);
                        notValidShortcut.setText(INSTRUCTION_ENTER_LETTER_LABEL);
                    } else {

                        changeShortcut();
                        dialog.dispose();
                        shortcutsDialog.dispose();
                        renderPanel();

                    }

                }
            });

            if (isPushed) {
                dialog.setLocationRelativeTo(null);
                dialog.setSize(WIDTH_EDIT_SHORTCUT_DIALOG, HEIGHT_EDIT_SHORTCUT_DIALOG);
                dialog.setLayout(new GridBagLayout());

                c.gridx = 0;
                c.gridy = 0;
                dialog.add(instructions, c);

                listPane.setLayout(new BoxLayout(listPane, BoxLayout.LINE_AXIS));
                listPane.add(ctrlKey);
                listPane.add(altKey);
                listPane.add(shiftKey);
                listPane.add(strokeKey);
                listPane.add(saveButton);

                c.gridx = 0;
                c.gridy = 25;

                dialog.add(listPane, c);
                c.gridx = 0;
                c.gridy = 30;
                dialog.add(notValidShortcut, c);
                listPane.setFocusable(true);
                listPane.requestFocusInWindow();

                dialog.setFocusable(false);
                dialog.setVisible(true);
            }
            isPushed = false;


            return new String(label);
        }

        /**
         * once the user has clicked save and is a valid binding, this method will
         * change the shortcut and save it to the database
         */
        public void changeShortcut() {

            boolean isCtrl = ctrlKey.isSelected();
            boolean isAlt = altKey.isSelected();
            boolean isShift = shiftKey.isSelected();
            switch (ROW_SHORTCUT_TABLE) {
                case 0:
                    sc.removeBinding(sc.getClearToolKeyCode(), sc.isCtrl_clearTool(), sc.isShift_clearTool(), sc.isAlt_clearTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.CLEAR_TOOL_SHORTCUT);
                    break;
                case 1:
                    sc.removeBinding(sc.getBrushToolKeyCode(), sc.isCtrl_brushTool(), sc.isShift_brushTool(), sc.isAlt_brushTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.BRUSH_TOOL_SHORTCUT);
                    break;

                case 2:
                    sc.removeBinding(sc.getAirBrushToolKeyCode(), sc.isCtrl_airBrushTool(), sc.isShift_airBrushTool(), sc.isAlt_airBrushTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.AIRBRUSH_TOOL_SHORTCUT);
                    break;
                case 3:
                    sc.removeBinding(sc.getEraserToolKeyCode(), sc.isCtrl_eraserTool(), sc.isShift_eraserTool(), sc.isAlt_eraserTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.ERASER_TOOL_SHORTCUT);
                    break;

                case 4:
                    sc.removeBinding(sc.getLineToolKeyCode(), sc.isCtrl_lineTool(), sc.isShift_lineTool(), sc.isAlt_lineTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.LINE_TOOL_SHORTCUT);
                    break;

                case 5:
                    sc.removeBinding(sc.getFanToolKeyCode(), sc.isCtrl_fanTool(), sc.isShift_fanTool(), sc.isAlt_fanTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.FAN_TOOL_SHORTCUT);
                    break;

                case 6:
                    sc.removeBinding(sc.getRectToolKeyCode(), sc.isCtrl_rectTool(), sc.isShift_rectTool(), sc.isAlt_rectTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.RECTANGLE_TOOL_SHORTCUT);
                    break;

                case 7:
                    sc.removeBinding(sc.getEllipseToolKeyCode(), sc.isCtrl_ellipseTool(), sc.isShift_ellipseTool(), sc.isAlt_ellipseTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.ELLIPSE_TOOL_SHORTCUT);
                    break;

                case 8:
                    sc.removeBinding(sc.getTriangleToolKeyCode(), sc.isCtrl_triangleTool(), sc.isShift_triangleTool(), sc.isAlt_triangleTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.TRIANGLE_TOOL_SHORTCUT);
                    break;

                case 9:
                    sc.removeBinding(sc.getPaintBucketToolKeyCode(), sc.isCtrl_paintBucketTool(), sc.isShift_paintBucketTool(), sc.isAlt_paintBucketTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.PAINTBUCKET_TOOL_SHORTCUT);
                    break;

                case 10:
                    sc.removeBinding(sc.getCelticKnotToolKeyCode(), sc.isCtrl_celticKnotTool(), sc.isShift_celticKnotTool(), sc.isAlt_celticKnotTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.CELTICKNOT_TOOL_SHORTCUT);
                    break;

                case 11:
                    sc.removeBinding(sc.getDnaToolKeyCode(), sc.isCtrl_dnaTool(), sc.isShift_dnaTool(), sc.isAlt_dnaTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.DNA_TOOL_SHORTCUT);
                    break;

                case 12:
                    sc.removeBinding(sc.getTextToolKeyCode(), sc.isCtrl_textTool(), sc.isShift_textTool(), sc.isAlt_textTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.TEXT_TOOL_SHORTCUT);
                    break;
                case 13:
                    sc.removeBinding(sc.getColorChooserToolKeyCode(), sc.isCtrl_colorChooserTool(), sc.isShift_colorChooserTool(), sc.isAlt_colorChooserTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.COLOR_CHOOSER_TOOL_SHORTCUT);
                    break;

                case 14:
                    sc.removeBinding(sc.getEyeDropToolKeyCode(), sc.isCtrl_eyeDropTool(), sc.isShift_eyeDropTool(), sc.isAlt_eyeDropTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.EYE_DROP_TOOL_SHORTCUT);
                    break;

                case 15:
                    sc.removeBinding(sc.getSelectionToolKeyCode(), sc.isCtrl_selectionTool(), sc.isShift_selectionTool(), sc.isAlt_selectionTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.SELECTION_TOOL_SHORTCUT);
                    break;

                case 16:
                    sc.removeBinding(sc.getImportKeyCode(), sc.isCtrl_import(), sc.isShift_import(), sc.isAlt_import());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.IMPORT_SHORTCUT);
                    break;
                case 17:
                    sc.removeBinding(sc.getExportKeyCode(), sc.isCtrl_export(), sc.isShift_export(), sc.isAlt_export());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.EXPORT_SHORTCUT);
                    break;
                case 18:
                    sc.removeBinding(sc.getPrintToolKeyCode(), sc.isCtrl_printTool(), sc.isShift_printTool(), sc.isAlt_printTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.PRINT_SHORTCUT);
                    break;
                default:
                    System.out.println(NO_SUCH_TOOL);
            }

        }

    }

    /**
     * can change the width of the columns for the button table
     */
    //taken from https://stackoverflow.com/questions/953972/java-jtable-setting-column-width
    public static void setColumnWidths(JTable table, int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]);
            } else break;
        }

    }
    public static void setColumnHeight(JTable table, int... widths) {
        int r = table.getRowCount();

        for(int i = 0;i<r;i++){
            table.setRowHeight (i, 20);
        }

    }


}
