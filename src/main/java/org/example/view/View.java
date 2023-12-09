package org.example.view;

import org.example.config.GameConfig;
import org.example.dto.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class View {
    private static  int menuWidth;
    private static  int menuHeight;
    private static  int tableWidth;
    private static  int tableHeight;
    private static  int gameWidth;
    private static  int gameHeight;
    private static  int gunWidth;
    private static  int gunHeight;
    private static  int bulletWidth;
    private static  int bulletHeight;
    private static  int soldierWidth;
    private static  int soldierHeight;
    private static  int parachutistWidth;
    private static  int parachutistHeight;
    private static  int helicopterWidth;
    private static  int helicopterHeight;

    public static int getGameHeight() {
        return gameHeight;
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static int getMenuHeight() {
        return menuHeight;
    }

    public static int getMenuWidth() {
        return menuWidth;
    }

    public static int getTableHeight() {
        return tableHeight;
    }

    public static int getTableWidth() {
        return tableWidth;
    }

    public static int getGunHeight() {
        return gunHeight;
    }

    public static int getGunWidth() {
        return gunWidth;
    }

    public static int getBulletHeight() {
        return bulletHeight;
    }

    public static int getBulletWidth() {
        return bulletWidth;
    }

    public static int getSoldierHeight() {
        return soldierHeight;
    }

    public static int getSoldierWidth() {
        return soldierWidth;
    }

    public static int getParachutistHeight() {
        return parachutistHeight;
    }

    public static int getParachutistWidth() {
        return parachutistWidth;
    }

    public static int getHelicopterHeight() {
        return helicopterHeight;
    }

    public static int getHelicopterWidth() {
        return helicopterWidth;
    }

    private MenuFrame menuFrame;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final ControllerListener controllerListener;

    private static int SCALING_FACTOR = 10;

    public View(ControllerListener controllerListener, GameConfig config) {
        this.controllerListener = controllerListener;
        try {
            // CR: move from view to a separate class
            File file = new File("src/main/resources/configuration.txt");
            Scanner scanner = new Scanner(file);
            menuWidth = Integer.parseInt(scanner.nextLine());
            menuHeight = Integer.parseInt(scanner.nextLine());
            tableWidth = Integer.parseInt(scanner.nextLine());
            tableHeight = Integer.parseInt(scanner.nextLine());
            gameWidth = Integer.parseInt(scanner.nextLine());
            gameHeight = Integer.parseInt(scanner.nextLine());
            gunWidth = Integer.parseInt(scanner.nextLine());
            gunHeight = Integer.parseInt(scanner.nextLine());
            bulletWidth = Integer.parseInt(scanner.nextLine());
            bulletHeight = Integer.parseInt(scanner.nextLine());
            soldierWidth = Integer.parseInt(scanner.nextLine());
            soldierHeight = Integer.parseInt(scanner.nextLine());
            parachutistWidth = Integer.parseInt(scanner.nextLine());
            parachutistHeight = Integer.parseInt(scanner.nextLine());
            helicopterWidth = Integer.parseInt(scanner.nextLine());
            helicopterHeight = Integer.parseInt(scanner.nextLine());
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runMenu(NewGameListener newGameListener, TableListener tableListener, ExitMenuListener exitMenuListener) {
        menuFrame = new MenuFrame(menuWidth, menuHeight);
        menuPanel = new MenuPanel(newGameListener, tableListener, exitMenuListener, menuWidth, menuHeight);
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        menuFrame.setContentPane(menuPanel);
    }

    public void runGame() {
        gameFrame = new GameFrame(gameWidth, gameHeight);
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