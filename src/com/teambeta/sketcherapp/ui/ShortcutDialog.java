package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.Shortcuts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//todo add a reset to defaults button
public class ShortcutDialog {
    MainUI mainUI;
    Shortcuts sc;
    JButton setDefaultShortcuts;

    public ShortcutDialog(MainUI mainUI, Shortcuts shortcuts) {
        this.mainUI = mainUI;
        this.sc = shortcuts;

    }

    // This class was taken and modified from  http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
     // Also got help from https://stackoverflow.com/questions/46985936/using-keybinding-and-action-map-in-java-for-shortcut-keys-for-buttons

    public void renderPanel() {

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{
                        {"Clear", printShortcut(sc.getKBShortcut(sc.CLEAR_TOOL_SHORTCUT), sc.isIsCtrl_clearTool(), sc.isIsShift_clearTool(), sc.isIsAlt_clearTool())},
                        {"Brush", printShortcut(sc.getKBShortcut(sc.BRUSH_TOOL_SHORTCUT), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool())},
                        {"Line", printShortcut(sc.getKBShortcut(sc.LINE_TOOL_SHORTCUT), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool())},
                        {"Import", printShortcut(sc.getKBShortcut(sc.IMPORT_SHORTCUT), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import())},
                        {"Export", printShortcut(sc.getKBShortcut(sc.EXPORT_SHORTCUT), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export())},
                },
                new Object[]{"Action", "Re-Assign"});

        JTable table = new JTable(dm);
        table.getColumn("Re-Assign").setCellRenderer(new ButtonRenderer());
        table.getColumn("Re-Assign").setCellEditor(
                new ButtonEditor(new JCheckBox()));
        JScrollPane scroll = new JScrollPane(table);

        // setColumnWidths(table, 450,200);
        JDialog dialog;
        JDialog.setDefaultLookAndFeelDecorated(true);
        dialog = new JDialog(mainUI.getMainFrame(), "Keyboard Shortcuts", true);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(700, 900);
        //dialog.setLayout(new GridLayout(1, 0));
        dialog.setLayout(new GridBagLayout());

        setDefaultShortcuts = new JButton("Default");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == setDefaultShortcuts){
                    sc.removeAllBindings();
                    dialog.dispose();
                    renderPanel();
                }
            }
        };

        setDefaultShortcuts.addActionListener(actionListener);
      //  widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
      //  widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        scroll.setMinimumSize(new Dimension(5000, 5000));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;



        dialog.add(scroll,c);
        dialog.add(Box.createRigidArea(new Dimension(15, 25)),c);
        c.gridx = 0;
        c.gridy = 3;
        dialog.add(setDefaultShortcuts);
        dialog.setVisible(true);


    }
    public void updateShortcuts() {


    }


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
        protected JButton button;
        private int keyCode;
        private int newKeyCode;
        private JToggleButton ctrlKey;
        private JToggleButton altKey;
        private JToggleButton shiftKey;
        private JButton strokeKey;
        private JButton saveButton;
        private JButton exitButton;
        private KeyStroke ks;
        private JDialog dialog;
        private JLabel instructions;
        boolean isValid = false;
        private String label;

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

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

           label = (value == null) ? "" : value.toString();
            button.setText(label);
            label = (value == null) ? "" : value.toString() + "/" + row;
            isPushed = true;

            return button;
        }

        public Object getCellEditorValue() {
            dialog = new JDialog(mainUI.getMainFrame(), "Edit Shortcut", true);
            String[] tokens = label.split("/"); //gets the row value

            JPanel listPane;
            listPane = new JPanel();

            listPane.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    //   updateMetaState(e);
                    int code = e.getKeyCode();
                    if (code != KeyEvent.VK_CONTROL && code != KeyEvent.VK_ALT && code != KeyEvent.VK_SHIFT && code != KeyEvent.VK_META && code != KeyEvent.VK_CAPS_LOCK) {
                        strokeKey.setText(KeyEvent.getKeyText(code));
                        keyCode = code;
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
            instructions = new JLabel("Click a combination of ctrl,alt and shift\n and type letter");

            ctrlKey.setFocusable(false);
            shiftKey.setFocusable(false);
            altKey.setFocusable(false);
            saveButton.setFocusable(false);
            exitButton.setFocusable(false);
            strokeKey.setFocusable(false);

            if (isPushed) {
                dialog.setLocationRelativeTo(null);
                dialog.setSize(450, 200);
                dialog.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                c.gridx = 0;
                c.gridy = 0;
                dialog.add(instructions, c);

                // dialog.add(Box.createRigidArea(new Dimension(15, 25)),c);
                listPane.setLayout(new BoxLayout(listPane, BoxLayout.LINE_AXIS));
                listPane.add(ctrlKey);
                listPane.add(altKey);
                listPane.add(shiftKey);
                listPane.add(strokeKey);
                c.gridx = 0;
                c.gridy = 25;

                dialog.add(listPane, c);
                listPane.setFocusable(true);
                listPane.requestFocusInWindow();

                dialog.setFocusable(false);
                dialog.setVisible(true);
            }
            isPushed = false;
            label = changeShortcut(Integer.parseInt(tokens[1]), strokeKey, keyCode);

            return new String(label);
        }

        public String changeShortcut(int row, JButton stroke, int code) {

            boolean isCtrl = ctrlKey.isSelected();
            boolean isAlt = altKey.isSelected();
            boolean isShift = shiftKey.isSelected();

            switch (row) {
                case 0:
                    if (sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        sc.removeBinding(sc.getClearToolKeyCode(), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                        sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.CLEAR_TOOL_SHORTCUT);
                        return printShortcut(sc.getKBShortcut(sc.CLEAR_TOOL_SHORTCUT), sc.isIsCtrl_clearTool(), sc.isIsShift_clearTool(), sc.isIsAlt_clearTool());
                    } else {
                        return printShortcut(sc.getKBShortcut(sc.CLEAR_TOOL_SHORTCUT), sc.isIsCtrl_clearTool(), sc.isIsShift_clearTool(), sc.isIsAlt_clearTool());
                    }

                case 1:
                    if (sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        sc.removeBinding(sc.getBrushToolKeyCode(), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool());
                        sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.BRUSH_TOOL_SHORTCUT);
                        return printShortcut(sc.getKBShortcut(sc.BRUSH_TOOL_SHORTCUT), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool());
                    } else {
                        return printShortcut(sc.getKBShortcut(sc.BRUSH_TOOL_SHORTCUT), sc.isIsCtrl_brushTool(), sc.isIsShift_brushTool(), sc.isIsAlt_brushTool());
                    }
                case 2:
                    if (sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        sc.removeBinding(sc.getLineToolKeyCode(), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                        sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.LINE_TOOL_SHORTCUT);
                        return printShortcut(sc.getKBShortcut(sc.LINE_TOOL_SHORTCUT), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                    } else {
                        return printShortcut(sc.getKBShortcut(sc.LINE_TOOL_SHORTCUT), sc.isIsCtrl_lineTool(), sc.isIsShift_lineTool(), sc.isIsAlt_lineTool());
                    }
                case 3:
                    if (sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        sc.removeBinding(sc.getImportKeyCode(), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import());
                        sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.IMPORT_SHORTCUT);
                        return printShortcut(sc.getKBShortcut(sc.IMPORT_SHORTCUT), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import());
                    } else {
                        return printShortcut(sc.getKBShortcut(sc.IMPORT_SHORTCUT), sc.isIsCtrl_import(), sc.isIsShift_import(), sc.isIsAlt_import());
                    }
                case 4:
                    if (sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)) {
                        sc.removeBinding(sc.getExportKeyCode(), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export());
                        sc.changeKeyBinding(newKeyCode, isCtrl, isShift, isAlt, Shortcuts.EXPORT_SHORTCUT);
                        return printShortcut(sc.getKBShortcut(sc.EXPORT_SHORTCUT), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export());
                    } else {
                        return printShortcut(sc.getKBShortcut(sc.EXPORT_SHORTCUT), sc.isIsCtrl_export(), sc.isIsShift_export(), sc.isIsAlt_export());
                    }
                default:
                    System.out.println("NO SUCH TOOL");

            }
            return null;
        }

        public KeyStroke getKeyStroke() {
            return KeyStroke.getKeyStroke(keyCode, getModifiers());
        }

        protected int getModifiers() {
            return (ctrlKey.isSelected() ? KeyEvent.CTRL_DOWN_MASK : 0)
                    | (altKey.isSelected() ? KeyEvent.ALT_DOWN_MASK : 0)
                    | (shiftKey.isSelected() ? KeyEvent.SHIFT_DOWN_MASK : 0);
            //| (metaKey.isSelected() ? KeyEvent.META_DOWN_MASK : 0);
        }

        protected void updateMetaState(KeyEvent evt) {
            updateMetaState(ctrlKey, evt.isControlDown());
            updateMetaState(altKey, evt.isAltDown());
            updateMetaState(shiftKey, evt.isShiftDown());
            //  updateMetaState(metaKey, evt.isMetaDown());
        }

        protected void updateMetaState(JToggleButton btn, boolean isPressed) {
            if (isPressed) {
                btn.setSelected(!btn.isSelected());
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
