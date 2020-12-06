package com.desktopApp;

import javax.swing.*;

public class App {
    private JPanel panelMain;

    public static void main(String[] args) {
        JFrame frame = new JFrame("winbeldon");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
