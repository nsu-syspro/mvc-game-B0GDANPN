package org.example.view;

import org.example.config.Config;
import org.example.dto.GameInfo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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


    record Score(String name, int score) {
    }

    // CR: add scores with names, add score manager
    /*class ScoreManager {

        // TODO: opens file

        private static ScoreManager instance = null;


        static ScoreManager instance() {
            if (instance == null) {
                instance = fromFile();
            }
            return instance;
        }

        public void addScore(String name, int score) {

        }

        public void saveScores() {

        }

        public List<Score> getScores() {

        }

    }*/

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


    public void setGameInfo(GameInfo gameInfo) {
        gamePanel.setGameInfo(gameInfo);
    }
}