package org.example.view;


import javax.swing.*;

public class MenuFrame extends JFrame {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 540;

    public MenuFrame() {
        setTitle("Paratrooper Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

}