package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class ParatrooperGame extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final List<GameObject> gameObjects;
    private final Gun gun;
    public ParatrooperGame() {
        setTitle("Paratrooper Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameObjects = new ArrayList<>();
        gun = new Gun(WIDTH / 2, HEIGHT - 50);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                createProjectile();
            }
        });
        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                gun.updatePosition(e.getX(),getY());
            }
        });

        setContentPane(gamePanel);


        Timer timer = new Timer(20, e -> {
            updateGame(getMousePosition());
            gamePanel.repaint();
        });
        timer.start();

        setVisible(true);
    }
    private void updateGame(Point mousePosition) {
        if (mousePosition != null) {
            gun.updatePosition(mousePosition.x, mousePosition.y);
        }
        for (GameObject object : gameObjects) {
            if (object instanceof Projectile) {
                ((Projectile) object).move();
            } else if (object instanceof Helicopter) {
                ((Helicopter) object).move();
                createParachutist((Helicopter) object);
            } else if (object instanceof Parachutist) {
                ((Parachutist) object).move();
            }
        }
        checkCollisions();
    }

    private void createProjectile() {
        gun.shoot(gameObjects);
    }
    private void createParachutist(Helicopter helicopter) {
        Random random = new Random();
        if (random.nextInt(100) < 5) {
            Parachutist parachutist = new Parachutist(helicopter.getX() + helicopter.getWidth() / 2, helicopter.getY());
            gameObjects.add(parachutist);
        }
    }

    private void createHelicopter() {
        Random random = new Random();
        int side = random.nextBoolean() ? 0 : WIDTH;
        Helicopter helicopter = new Helicopter(side, 50);
        gameObjects.add(helicopter);
    }
    private void createSoldier(int x, int y) {
        Soldier soldier = new Soldier(x, y);
        gameObjects.add(soldier);
    }

    private void checkCollisions() {
        List<GameObject> objectsToRemove = new ArrayList<>();
        for (GameObject object : gameObjects) {
            if (object instanceof Helicopter) {
                for (GameObject projectile : gameObjects) {
                    if (projectile instanceof Projectile && object.getBounds().intersects(projectile.getBounds())) {
                        objectsToRemove.add(object);
                        objectsToRemove.add(projectile);
                    }
                }
            } else if (object instanceof Parachutist) {
                if (object.getY() >= HEIGHT - 50) {
                    gameObjects.remove(object);
                    Soldier soldier = new Soldier(object.getX(), HEIGHT - 50);
                    gameObjects.add(soldier);
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);

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
    private void checkSoldierCount() {
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



    private void endGame() {
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (GameObject object : gameObjects) {
            object.draw(g);
        }
        gun.draw(g);
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (GameObject object : gameObjects) {
                object.draw(g);
            }
            gun.draw(g);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParatrooperGame game = new ParatrooperGame();
            game.setVisible(true);
        });
    }

}
class GameObject {
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x=x;
    }

    public int getY() {
        return y;
    }
    public void setY(int x) {
        this.x=x;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    public int getWidth() {
        return 0; // Override in subclasses
    }

    public int getHeight() {
        return 0; // Override in subclasses
    }

    public void draw(Graphics g) {
        // Draw the object, override in subclasses
    }
}

class Gun extends GameObject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;
    private static final int SHOOT_DELAY = 200;
    private double angle;
    private long lastShootTime;

    public Gun(int x, int y) {
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
        this.lastShootTime = System.currentTimeMillis();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Graphics g) {
        try {
            final BufferedImage image = ImageIO.read(new File("src/main/java/org.example/gunbase.png"));
            g.drawImage(image,0,0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Draw the gun image here
    }

     public void updatePosition(int mouseX,int mouseY) {
         angle = Math.atan2(mouseY - y - HEIGHT / 2, mouseX - x - WIDTH / 2);

     }

    public void shoot(List<GameObject> projectiles) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > SHOOT_DELAY) {
            lastShootTime = currentTime;


            // Create projectile with velocity towards mouse cursor
            Projectile projectile = new Projectile(x + WIDTH / 2, y + HEIGHT / 2, angle);
            projectiles.add(projectile);
        }
    }
}
class Projectile extends GameObject {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int SPEED = 5;
    private double angle;

    public Projectile(int x, int y, double angle) {
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
        this.angle = angle;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Graphics g) {
        try {
            final BufferedImage image = ImageIO.read(new File("src/main/java/org.example/bullet.png"));
            g.drawImage(image,0,0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void move() {
        // Move the projectile based on the calculated angle
        x += SPEED * Math.cos(angle);
        y += SPEED * Math.sin(angle);
    }
}

class Helicopter extends GameObject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;
    private static final int SPEED = 3;
    private int VECTOR;

    public Helicopter(int x, int y) {
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
        Random random = new Random();
        boolean chooseA = random.nextBoolean();
        this.VECTOR = chooseA ? 1 : -1;

    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Graphics g) {
        try {
            BufferedImage image;
            if (VECTOR==1){
                image=ImageIO.read(new File("src/main/java/org.example/minihelicopter1.png"));
                g.drawImage(image,10,10, null);
            }
            else{
                image=ImageIO.read(new File("src/main/java/org.example/minihelicopter2.png"));
                g.drawImage(image,790,10, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Draw the helicopter image here
    }

    public void move() {
        x += SPEED*VECTOR;
    }
}

class Parachutist extends GameObject {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 30;
    private static final int SPEED = 2;

    public Parachutist(int x, int y) {
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Graphics g) {

        // Draw the parachutist image here
    }

    public void move() {
        y += SPEED;
    }
}

class Soldier extends GameObject {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 30;

    public Soldier(int x, int y) {
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Graphics g) {
        // Draw the soldier image here
    }
}