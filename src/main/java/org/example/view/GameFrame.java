package org.example.view;

import org.example.config.GameConfig;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(GameConfig gameConfig) {
        int gameWidth = gameConfig.getGameWidth();
        int gameHeight = gameConfig.getGameHeight();
        setTitle("Paratrooper Game");
        setSize(gameWidth, gameHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
