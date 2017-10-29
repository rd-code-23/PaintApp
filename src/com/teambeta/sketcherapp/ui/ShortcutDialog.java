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
    JDialog shortcutsDialog;
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

        JDialog.setDefaultLookAndFeelDecorated(true);
        shortcutsDialog = new JDialog(mainUI.getMainFrame(), "Keyboard Shortcuts", true);
        shortcutsDialog.setLocationRelativeTo(null);
        shortcutsDialog.setSize(700, 900);
        //dialog.setLayout(new GridLayout(1, 0));
        shortcutsDialog.setLayout(new GridBagLayout());

        setDefaultShortcuts = new JButton("Default");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == setDefaultShortcuts){
                    sc.removeAllBindings();
                    shortcutsDialog.dispose();
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



        shortcutsDialog.add(scroll,c);
        shortcutsDialog.add(Box.createRigidArea(new Dimension(15, 25)),c);
        c.gridx = 0;
        c.gridy = 3;
        shortcutsDialog.add(setDefaultShortcuts);
        shortcutsDialog.setVisible(true);


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
        boolean isValid = false;
        private String label;

        int theRow;
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

            theRow = row;

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
                  //  if(e.getSource() == saveButton){
                    //    System.out.println("saved");

                    boolean isCtrl = ctrlKey.isSelected();
                    boolean isAlt = altKey.isSelected();
                    boolean isShift = shiftKey.isSelected();
                    if(!sc.isValidKeyBinding(newKeyCode, isCtrl, isShift, isAlt)){
                        notValidShortcut.setForeground(Color.red);
                        //notValidShortcut.setBackground(Color.blue);
                        notValidShortcut.setOpaque(true);
                        notValidShortcut.setText("That Binding is Already Taken!");

                    } else {

                        changeShortcut();
                        dialog.dispose();
                        shortcutsDialog.dispose();
                        renderPanel();

                    }


                    //}
                }
            });

            if (isPushed) {
                dialog.setLocationRelativeTo(null);
                dialog.setSize(450, 200);
                dialog.setLayout(new GridBagLayout());


                c.gridx = 0;
                c.gridy = 0;
                dialog.add(instructions, c);

                // dialog.add(Box.createRigidArea(new Dimension(15, 25)),c);
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
                dialog.add(notValidShortcut,c);
                listPane.setFocusable(true);
                listPane.requestFocusInWindow();

                dialog.setFocusable(false);
                dialog.setVisible(true);
            }
            isPushed = false;
          //  if(newKeyCode != -1)
           // label = changeShortcut();

            return new String(label);
        }

        public void changeShortcut() {

            boolean isCtrl = ctrlKey.isSelected();
            boolean isAlt = altKey.isSelected();
            boolean isShift = shiftKey.isSelected();

            switch (theRow) {
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
