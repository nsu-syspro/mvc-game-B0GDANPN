package org.example.view;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(int gameWidth, int gameHeight) {
        setTitle("Paratrooper Game");
        setSize(gameWidth, gameHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
