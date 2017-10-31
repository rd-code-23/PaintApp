package com.teambeta.sketcherapp.model;

import javax.swing.*;


public class AboutMenu extends JFrame {


private static final String ABOUT_MESSAGE_TEXT = "Simon Fraser University\n" +
                                                 "CMPT 106, Fall 2017\n" +
                                                 "Team Beta Group Project: Beta Paint\n" +
                                                 "Version 1.0\n\n" +
                                                 "Team Members:\n" +
                                                 "Andrea Renney, Darryl Fonseka, Zongyuan Shao, Ali Maladwala, Mathieu Laflamme, " +
                                                 "Kyle Saburao, Larren Canapi, Rishi Dholliwar\n\n" +
                                                 "Our main objective is to design and implement a graphics editor, BetaPaint, such that it can " +
                                                 "be used effectively to create simple,\ncolorful graphics. By using a streamlined and clean " +
                                                 "design, the interface should be simple and easy to use without having \nany prior experience. " +
                                                 "This will ensure BetaPaint is accessible to users with varying levels of experience.\n\n" +
                                                 "Acknowledgements:\n" +
                                                 "Java JDK, IntelliJ IDEA, GitLab";



    public void PrepareAbout() {
        JOptionPane.showMessageDialog(null, ABOUT_MESSAGE_TEXT, "About", JOptionPane.PLAIN_MESSAGE);
         }



}
