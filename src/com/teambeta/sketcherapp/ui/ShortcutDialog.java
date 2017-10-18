package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.Main;
import org.omg.CORBA.TRANSACTION_MODE;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class ShortcutDialog {
    MainUI mainUI;

    public ShortcutDialog(MainUI mainUI) {
        this.mainUI = mainUI;


    }

    // This class was  taken and modified from  http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm

    public void renderPanel() {

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{{"Clear", "shift+c"},
                {"Brush", "ctrl+c"}}, new Object[]{"Action", "Re-Assign"});

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
        dialog.setLayout(new GridLayout(1, 0));

        dialog.add(scroll);
        dialog.setVisible(true);
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
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                //
                //
                JOptionPane.showMessageDialog(button, "Previous: " + label);
                // System.out.println(label + ": Ouch!");
            }
            isPushed = false;
            return new String(label);
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
