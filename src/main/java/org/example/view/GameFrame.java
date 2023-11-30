package org.example.view;

import javax.swing.*;

public class GameFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    public GameFrame() {
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
