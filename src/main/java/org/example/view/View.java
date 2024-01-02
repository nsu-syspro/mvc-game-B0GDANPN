package org.example.view;

import org.example.config.Config;
import org.example.dto.GameInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class View {
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final Config config;
    private final ControllerListener controllerListener;

    public View(ControllerListener controllerListener, Config config) {
        this.controllerListener = controllerListener;
        this.config = config;
    }

    public void runMenu(NewGameListener newGameListener, TableListener tableListener) {
        MenuFrame menuFrame = new MenuFrame(config.menu().width(), config.menu().height());
        MenuPanel menuPanel = new MenuPanel(newGameListener, tableListener, config.menu().width(), config.menu().height());
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        menuFrame.setContentPane(menuPanel);
    }

    public void runGame() {
        gameFrame = new GameFrame(config);
        gamePanel = new GamePanel();
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerListener.createBullet();
            }
        });
        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controllerListener.updateGun(e.getX(), e.getY());
            }
        });
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setContentPane(gamePanel);
    }

    public String getUserName() {
        String input = JOptionPane.showInputDialog("Enter a name player:");
        return input;
    }

    public void showTable() {
        TableFrame tableFrame = new TableFrame(config.table().width(), config.table().height(), config.resultName());
    }

    public void endGame() {
        gameFrame.dispose();
    }


    public void setGameInfo(GameInfo gameInfo) {
        gamePanel.setGameInfo(gameInfo);
    }
}