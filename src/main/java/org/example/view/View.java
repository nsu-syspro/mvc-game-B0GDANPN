package org.example.view;

import org.example.config.GameConfig;
import org.example.dto.GameInfo;
import org.example.dto.IndicesReduced;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class View {
    private MenuFrame menuFrame;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final GameConfig gameConfig;
    private final ControllerListener controllerListener;

    private static int SCALING_FACTOR = 10;//Хз зачем

    public View(ControllerListener controllerListener, GameConfig config) {
        this.controllerListener = controllerListener;
        this.gameConfig = config;
    }

    public void runMenu(NewGameListener newGameListener, TableListener tableListener, ExitMenuListener exitMenuListener) {
        menuFrame = new MenuFrame(gameConfig.getMenuWidth(),
                gameConfig.getMenuHeight());
        menuPanel = new MenuPanel(newGameListener, tableListener, exitMenuListener, gameConfig.getMenuWidth(), gameConfig.getMenuHeight());
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        menuFrame.setContentPane(menuPanel);
    }

    public void runGame() {
        gameFrame = new GameFrame(gameConfig.getGameWidth(), gameConfig.getGameHeight());
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


    public void showTable(int currentScore) {
        try {
            File file = new File("fileRES.txt");
            StringBuilder sb = new StringBuilder();
            sb.append("Best record is ");
            int number;
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    number = Integer.parseInt(scanner.nextLine());
                } else {
                    number = 0;
                }
                scanner.close();
            } else {
                FileWriter writer = new FileWriter(file);
                number = 0;
                writer.write("0");
                writer.close();
            }
            sb.append(number).append("\n");
            sb.append("Current result is ");
            sb.append(currentScore).append("\n");
            if (currentScore > number) {
                FileWriter writer = new FileWriter(file);
                writer.write(String.valueOf(currentScore));
                writer.close();
            }
            TableFrame tableRecords = new TableFrame(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void endGame() {
        gameFrame.dispose();
    }

    public void exitMenu() {
        System.exit(0);
    }

    public IndicesReduced getIndicesReducedObjects() {
        return gamePanel.getIndicesReducedObjects();
    }

    public void setGameInfo(GameInfo gameInfo) {
        gamePanel.setGameInfo(gameInfo);
    }
}