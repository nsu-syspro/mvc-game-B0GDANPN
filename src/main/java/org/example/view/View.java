package org.example.view;

import org.example.dto.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class View implements TableListener, ExitMenuListener {
    private static final int menuWidth = 900;
    private static final int menuHeight = 540;
    private static final int tableWidth = 600;
    private static final int tableHeight = 600;
    private static final int gameWidth = 1200;
    private static final int gameHeight = 800;
    private static final int gunWidth = 77;
    private static final int gunHeight = 130;
    private static final int bulletWidth = 32;
    private static final int bulletHeight = 32;
    private static final int soldierWidth = 72;
    private static final int soldierHeight = 72;
    private static final int parachutistWidth = 72;
    private static final int parachutistHeight = 90;
    private static final int helicopterWidth = 200;
    private static final int helicopterHeight = 95;

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
    private final NewGameListener newGameListener;
    private final TableListener tableListener;
    private final ExitMenuListener exitMenuListener;

    public View(ControllerListener controllerListener, NewGameListener newGameListener, TableListener tableListener, ExitMenuListener exitMenuListener) {
        this.controllerListener = controllerListener;
        this.newGameListener = newGameListener;
        this.tableListener = tableListener;
        this.exitMenuListener = exitMenuListener;
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

    @Override
    public void showTable() {
        try {
            File file = new File("file.txt");
            StringBuilder sb = new StringBuilder();
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    int number = Integer.parseInt(scanner.nextLine());
                    sb.append(number).append("\n");
                }
                scanner.close();
            } else {
                FileWriter writer = new FileWriter(file);
                writer.close();
            }
            TableFrame tableRecords = new TableFrame(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void endGame(int score){
        try {
            FileWriter writer = new FileWriter("file.txt", true);
            writer.write(score + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameFrame.dispose();
    }
    @Override
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