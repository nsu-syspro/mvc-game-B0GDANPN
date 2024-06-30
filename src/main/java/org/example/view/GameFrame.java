package org.example.view;

import org.example.config.Config;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(Config config) {
        int gameWidth = config.game().width();
        int gameHeight = config.game().height();
        setTitle("Paratrooper Game");
        setSize(gameWidth, gameHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
