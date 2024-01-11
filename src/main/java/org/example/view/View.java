package org.example.view;

import org.example.config.Config;
import org.example.dto.GameInfo;
import org.example.utils.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class View {
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final Config config;
    private final ControllerListener controllerListener;

    public View(ControllerListener controllerListener, Config config) {
        this.controllerListener = controllerListener;
        this.config = config;
    }

    public void runMenu(NewGameListener newGameListener, TableListener tableListener,ExitMenuListener exitMenuListener) {
        MenuFrame menuFrame = new MenuFrame(config.menu().width(), config.menu().height());
        MenuPanel menuPanel = new MenuPanel(newGameListener, tableListener,exitMenuListener, config.menu().width(), config.menu().height());
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
        return JOptionPane.showInputDialog("Enter a name player:");
    }

    public void showTable(List<Score> scores) {
        new TableFrame(config.table().width(), config.table().height(),scores);
    }

    public void endGame(String name, int score) {
        JOptionPane.showMessageDialog(null,"Your score: " + score + " Your name: " + name);
        gameFrame.dispose();
    }


    public void setGameInfo(GameInfo gameInfo) {
        gamePanel.setGameInfo(gameInfo);
    }
}