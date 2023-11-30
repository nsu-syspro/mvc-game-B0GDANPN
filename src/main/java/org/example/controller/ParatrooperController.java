package org.example.controller;

import org.example.dto.GameObject;
import org.example.view.*;
import org.example.model.*;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class ParatrooperController implements Runnable, NewGameListener, TableListener, ExitMenuListener, ControllerListener {
    private MenuFrame menuFrame;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private int score;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Gun gun;


    public void run() {
        menuFrame = new MenuFrame();
        menuPanel = new MenuPanel(this, this, this, menuFrame.getWidth(), menuFrame.getHeight());
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        menuFrame.setContentPane(menuPanel);
    }

    @Override
    public void newGame() {
        score = 0;
        gameFrame = new GameFrame();
        gun = new Gun(gameFrame.getWidth() / 2, gameFrame.getHeight());
        gameObjects.add(gun);
        gamePanel = new GamePanel(this, gameFrame.getWidth(), gameFrame.getHeight(), gameObjects);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setContentPane(gamePanel);
        Timer timer = new Timer(19, e -> {
            this.updateGame(gameFrame.getMousePosition());
            gamePanel.repaint();
        });
        timer.start();
        Timer helicopterTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHelicopter(gameFrame.getWidth());
                gamePanel.repaint();
            }
        });
        helicopterTimer.start();
        Timer parachutistTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = gameObjects.size();
                Random random = new Random();
                for (int i = 1; i < size; i++) {
                    if (gameObjects.get(i) instanceof Helicopter) {
                        if (random.nextInt(100) < 20) {
                            createParachutist((Helicopter) gameObjects.get(i));
                        }
                    }
                }
                gamePanel.repaint();
            }
        });
        parachutistTimer.start();
    }


    private void updateGame(Point mousePosition) {
        if (mousePosition != null) {
            updateGunPosition(mousePosition.x, mousePosition.y);
        }
        for (int i = 1; i < gameObjects.size(); i++) {
            gameObjects.get(i).move();
        }
        checkHit();
        paratrooperToSoldier();
        checkCountSoldiers();
        checkCollisions();
    }

    @Override
    public void updateGunPosition(double mouseX, double mouseY) {
        gun.updatePosition(mouseX, mouseY);
    }

    private void checkCollisions() {
        Rectangle rectangleScreen = new Rectangle(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
        Set<GameObject> objectsToRemove = new HashSet<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Parachutist) {
                if (!object.getBounds().intersects(rectangleScreen)) {
                    objectsToRemove.add(object);
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }

    @Override
    public void createBullet() {
        gun.shoot(gameObjects);
    }

    public void increaseScore() {
        score++;
    }

    private void createParachutist(Helicopter helicopter) {
        Parachutist parachutist = new Parachutist(helicopter.getX() + helicopter.getWidth() / 2, helicopter.getY());
        gameObjects.add(parachutist);
    }

    private void createHelicopter(int widthScreen) {
        Random random = new Random();
        boolean vector = random.nextBoolean();
        Helicopter helicopter = new Helicopter(widthScreen, vector);
        gameObjects.add(helicopter);
    }


    private void checkHit() {
        Set<GameObject> objectsToRemove = new HashSet<>();
        Set<Bullet> bullets = gameObjects.stream().filter(o -> o instanceof Bullet).map(o -> (Bullet) o).collect(toSet());
        Set<GameObject> otherObjects = gameObjects.stream().filter(o -> !(o instanceof Bullet) && !(o instanceof Gun) && !(o instanceof Soldier)).collect(toSet());
        for (Bullet bullet : bullets) {
            for (GameObject otherObject : otherObjects) {
                if (bullet.getBounds().intersects(otherObject.getBounds())) {
                    increaseScore();
                    objectsToRemove.add(bullet);
                    objectsToRemove.add(otherObject);
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);
    }

    private void paratrooperToSoldier() {
        Set<GameObject> objectsToRemove = new HashSet<>();
        Set<GameObject> otherObjects = gameObjects.stream().filter(o -> !(o instanceof Bullet) && !(o instanceof Gun) && !(o instanceof Soldier)).collect(toSet());


        for (GameObject object : otherObjects) {
            if (object instanceof Parachutist) {
                if (object.getY() >= gameFrame.getHeight() - 100) {
                    objectsToRemove.add(object);
                    Soldier soldier = new Soldier(object.getX(), gameFrame.getHeight() - 72);
                    gameObjects.add(soldier);
                }
            }

        }
        gameObjects.removeAll(objectsToRemove);
    }

    private void checkCountSoldiers() {
        int soldierCount = 0;
        for (GameObject object : gameObjects) {
            if (object instanceof Soldier) {
                soldierCount++;
            }
        }
        if (soldierCount >= 5) {
            endGame();
        }
    }


    private void endGame() {//TODO jj
        gameFrame.dispose();
        try {
            FileWriter writer = new FileWriter("file.txt", true);
            writer.write(score + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @Override
    public void exitMenu() {
        System.exit(0);
    }


}