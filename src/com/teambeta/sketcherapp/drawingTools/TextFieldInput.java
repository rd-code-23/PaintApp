package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Custom text field used for TextTool class. Text field that closes upon the "Enter" key.
 */
public class TextFieldInput extends JDialog {
    private String userInput;
    private final int MOUSE_DEVIATION = 12;
    private final int WIDTH = 15;
    private final int HEIGHT = 2;

    /**
     * Constructor.
     * @param textColor color for text input
     * @param locationX x mouse coordinate
     * @param locationY y mouse coordinate
     */
    public TextFieldInput(Color textColor, int locationX, int locationY,Font myFont) {
        super(new JFrame(), true);
        setUndecorated(true);
        setLocation(locationX - MOUSE_DEVIATION, locationY - MOUSE_DEVIATION);
        setLayout(new BorderLayout());
        TextField textField = new TextField();
        textField.setFont(myFont);
        textField.setForeground(textColor);
        textField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                userInput = textField.getText();
                setVisible(false);
                dispose();
            }});
        setPreferredSize(new Dimension(WIDTH*myFont.getSize(), (HEIGHT*myFont.getSize())));
        getContentPane().add(BorderLayout.CENTER, textField);
        pack();
        setVisible(true);
    }

    /**
     * Returns the text entered by user
     * @return user's input
     */
    public String getUserInput() {
        return userInput;
    }
}
