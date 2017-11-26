package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Displays splash screen for a given amount of time.
 * Referenced from JAVAWORLD:
 * https://www.javaworld.com/article/2077467/core-java/java-tip-104--make-a-splash-with-swing.html
 */
public class BetaSplashScreen extends JWindow {
    private static final String RES_PATH = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res";
    private static final String SPLASH_SCREEN_DIRECTORY = RES_PATH + File.separator + "splash.png";

    public BetaSplashScreen(int splashDisplayDuration) {
        super();
        JLabel l = new JLabel(new ImageIcon(SPLASH_SCREEN_DIRECTORY));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width / 2 - (labelSize.width / 2),
                screenSize.height / 2 - (labelSize.height / 2));
        final Runnable closerRunner = new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        };
        Runnable waitRunner = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(splashDisplayDuration);
                    SwingUtilities.invokeAndWait(closerRunner);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
    }
}
