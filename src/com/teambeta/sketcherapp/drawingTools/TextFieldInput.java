package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
     * @param textColor color for text input
     * @param locationX x mouse coordinate
     * @param locationY y mouse coordinate
     */
    public TextFieldInput(Color textColor, int locationX, int locationY, Font font) {
        super(new JFrame(), true);
        this.font = font;
        setUndecorated(true);
        setLocation(locationX - MOUSE_DEVIATION_X, locationY - MOUSE_DEVIATION_Y);
        setLayout(new BorderLayout());
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setForeground(textColor);
        textField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                userInput = textField.getText();
                setVisible(false);
                dispose();
            }});
        setPreferredSize(new Dimension(WIDTH*font.getSize(), (HEIGHT*font.getSize())));
        getContentPane().add(BorderLayout.CENTER, textField);
        pack();
        setVisible(true);
    }

    public void setFontType(String fontType) {
        font = new Font(fontType, 0, font.getSize());
        setFont(font);
    }

    /**
     * Returns the text entered by user
     * @return user's input
     */
    public String getUserInput() {
        return userInput;
    }
}
