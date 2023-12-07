package org.example.model;

import org.example.dto.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Gun extends GameObject {
    private static final int WIDTH = 77;
    private static final int HEIGHT = 130;
    private static final int SHOOT_DELAY = 200;
    private double angle;
    private long lastShootTime;

    public Gun(int x, int y) {
        this.setX(x - WIDTH);
        this.setY(y - HEIGHT);
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
            BufferedImage image = ImageIO.read(new File("src/main/resources/gunbase.png"));
            g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePosition(double mouseX, double mouseY) {
        angle = Math.atan2(mouseY - this.getY() - HEIGHT / 2, mouseX - this.getX() - WIDTH / 2);

    }

    public void shoot(ArrayList<GameObject> objects) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > SHOOT_DELAY) {
            lastShootTime = currentTime;
            Bullet bullet = new Bullet(this.getX() + WIDTH / 2, this.getY() + HEIGHT / 2, angle);
            objects.add(bullet);
        }
    }
}