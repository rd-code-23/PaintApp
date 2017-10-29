package com.teambeta.sketcherapp.drawingTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Custom text field used for TextTool class. Text field that closes upon the "Enter" key.
 */
public class TextFieldInput extends JDialog {
    private String userInput;
    private final int MOUSE_DEVIATION_X = 0;
    private final int MOUSE_DEVIATION_Y = 35;
    private final int WIDTH = 15;
    private final int HEIGHT = 2;
    private Font font;

    /**
     * Constructor.
     *
     * @param textColor color for text input
     * @param locationX x mouse coordinate
     * @param locationY y mouse coordinate
     */
    public TextFieldInput(Color textColor, int locationX, int locationY, Font font) {
        super(new JFrame(), true);
        userInput = "";
        this.font = font;
        setUndecorated(true);
        setLocation(locationX - MOUSE_DEVIATION_X, locationY - MOUSE_DEVIATION_Y);
        setLayout(new BorderLayout());
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setFocusable(true);
        textField.setForeground(textColor);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userInput = textField.getText();
                closeTextField(textField);
            }
        });
        textField.addKeyListener(new KeyAdapter() {
                                     @Override
                                     public void keyPressed(KeyEvent e) {
                                         if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                                             closeTextField(textField);
                                         }
                                     }
                                 }
        );
        setPreferredSize(new Dimension(WIDTH * font.getSize(), (HEIGHT * font.getSize())));
        getContentPane().add(BorderLayout.CENTER, textField);
        pack();
        setVisible(true);
    }

    /**
     * Close text field.
     *
     * @param textField to close.
     */
    private void closeTextField(JTextField textField) {
        setVisible(false);
        dispose();
    }

    /**
     * Set the font name to the one selected in the font chooser drop-down.
     *
     * @param fontType name (or style) of the font.
     */
    public void setFontType(String fontType) {
        font = new Font(fontType, Font.PLAIN, font.getSize());
        setFont(font);
    }

    /**
     * Returns the text entered by user
     *
     * @return user's input
     */
    public String getUserInput() {
        return userInput;
    }
}