package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.Shortcuts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;


public class ShortcutDialog {

    private static final int SHORTUCT_DIALOG_WIDTH = 700;
    private static final int SHORTCUT_DIALOG_HEIGHT = 900;
    private static final String KEYBOARD_SHORTCUTS_TITLE = "Keyboard Shortcuts";
    private static final String RE_ASSIGN_BUTTON_TABLE_COL = "Re-Assign";
    private static final String RE_ASSIGN_SHORTCUT_TABLE_COL = RE_ASSIGN_BUTTON_TABLE_COL;
    private static final String CLEAR_SHORTCUT_TABLE = "Clear";
    private static final String BRUSH_SHORTCUT_TABLE = "Brush";
    private static final String LINE_SHORTCUT_TABLE = "Line";
    private static final String IMPORT_SHORTCUT_TABLE = "Import";
    private static final String EXPORT_SHORTCUT_TABLE = "Export";
    private static final String ACTION_SHORTCUT_TABLE_COL = "Action";
    private static final String DEFAULT_BUTTON = "Default";
    private static final String APPLY_BUTTON = "Apply";

    private MainUI mainUI;
    private Shortcuts sc;
    private JButton setDefaultShortcuts;
    private JButton applyButton;
    private JDialog shortcutsDialog;

    public ShortcutDialog(MainUI mainUI, Shortcuts shortcuts) {
        this.mainUI = mainUI;
        this.sc = shortcuts;
    }

    // This class was taken and modified from  http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
    // Also got help from https://stackoverflow.com/questions/46985936/using-keybinding-and-action-map-in-java-for-shortcut-keys-for-buttons

    /**
     * remderes the panel for the shortcut dialog
     */
    public void renderPanel() {

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{
                        {CLEAR_SHORTCUT_TABLE, printShortcut(sc.getKBShortcut(sc.CLEAR_TOOL_SHORTCUT), sc.isIsCtrl_clearTool(), sc.isIsShift_clearTool(), sc.isIsAlt_clearTool())},
                        {BRUSH_SHORTCUT_TABLE, printShortcut(sc.getKBShortcut(sc.BRUSH_TOOL_SHORTCUT), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool())},
                        {LINE_SHORTCUT_TABLE, printShortcut(sc.getKBShortcut(sc.LINE_TOOL_SHORTCUT), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool())},
                        {IMPORT_SHORTCUT_TABLE, printShortcut(sc.getKBShortcut(sc.IMPORT_SHORTCUT), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import())},
                        {EXPORT_SHORTCUT_TABLE, printShortcut(sc.getKBShortcut(sc.EXPORT_SHORTCUT), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export())},
                },
                new Object[]{ACTION_SHORTCUT_TABLE_COL, RE_ASSIGN_SHORTCUT_TABLE_COL});

        JTable table = new JTable(dm);
        table.getColumn(RE_ASSIGN_SHORTCUT_TABLE_COL).setCellRenderer(new ButtonRenderer());
        table.getColumn(RE_ASSIGN_SHORTCUT_TABLE_COL).setCellEditor(
                new ButtonEditor(new JCheckBox()));
        JScrollPane scroll = new JScrollPane(table);


        JDialog.setDefaultLookAndFeelDecorated(true);
        shortcutsDialog = new JDialog(mainUI.getMainFrame(), KEYBOARD_SHORTCUTS_TITLE, true);
        shortcutsDialog.setLocationRelativeTo(null);
        shortcutsDialog.setSize(SHORTUCT_DIALOG_WIDTH, SHORTCUT_DIALOG_HEIGHT);
        shortcutsDialog.setLayout(new GridBagLayout());

        setDefaultShortcuts = new JButton(DEFAULT_BUTTON);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == setDefaultShortcuts) {
                    sc.removeAllBindings();
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

        setDefaultShortcuts.addActionListener(actionListener);
        applyButton.addActionListener(actionListener2);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        shortcutsDialog.add(scroll, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = -1;
        c.gridy = 3;
        shortcutsDialog.add(setDefaultShortcuts, c);
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
     * prints the shortcut
     * @param keyCode
     * @param useControl
     * @param useShift
     * @param useAlt
     * @return
     */
    String printShortcut(int keyCode, boolean useControl, boolean useShift, boolean useAlt) {

        String string = "";

        if (useControl) {
            string += "Ctrl+";
        }

        if (useShift) {
            string += "Shift+";
        }

        if (useAlt) {
            string += "Alt+";
        }

        string += (char) keyCode;

        return string;
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
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


    class ButtonEditor extends DefaultCellEditor {
        private static final String EDIT_SHORTCUT_TITLE_EDIT_SHORTCUT_DIALOG = "Edit Shortcut";
        private static final int WIDTH_EDIT_SHORTCUT_DIALOG = 450;
        private static final int HEIGHT_EDIT_SHORTCUT_DIALOG = 200;

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
            isPushed = true;

            return button;
        }

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

            strokeKey = new JButton("-");
            saveButton = new JButton("Save");
            exitButton = new JButton("Cancel");
            ctrlKey = new JToggleButton("ctrl");
            altKey = new JToggleButton("alt");
            shiftKey = new JToggleButton("shift");
            instructions = new JLabel("Toggle ctrl,alt and shift.Then type a letter");
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
                        notValidShortcut.setText("That Binding is Already Taken!");

                    } else if (newKeyCode == -1) {
                        notValidShortcut.setForeground(Color.red);
                        notValidShortcut.setOpaque(true);
                        notValidShortcut.setText("You need to enter a letter!");
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

        public void changeShortcut() {

            boolean isCtrl = ctrlKey.isSelected();
            boolean isAlt = altKey.isSelected();
            boolean isShift = shiftKey.isSelected();

            switch (ROW_SHORTCUT_TABLE) {
                case 0:
                    sc.removeBinding(sc.getClearToolKeyCode(), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.CLEAR_TOOL_SHORTCUT);
                    break;
                case 1:
                    sc.removeBinding(sc.getBrushToolKeyCode(), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.BRUSH_TOOL_SHORTCUT);
                    break;
                case 2:
                    sc.removeBinding(sc.getLineToolKeyCode(), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.LINE_TOOL_SHORTCUT);
                    break;
                case 3:
                    sc.removeBinding(sc.getImportKeyCode(), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.IMPORT_SHORTCUT);
                    break;
                case 4:
                    sc.removeBinding(sc.getExportKeyCode(), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export());
                    sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.EXPORT_SHORTCUT);
                    break;

                default:
                    System.out.println("NO SUCH TOOL");

            }

        }

    }

    //taken from https://stackoverflow.com/questions/953972/java-jtable-setting-column-width
    public static void setColumnWidths(JTable table, int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]);
            } else break;
        }

    }
}
