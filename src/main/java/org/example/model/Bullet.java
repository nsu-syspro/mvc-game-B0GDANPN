package org.example.model;

import org.example.dto.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet extends GameObject {
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;
    private static final int SPEED = 5;
    private double angle;

    public Bullet(double x, double y, double angle) {
        this.setX(x - WIDTH);
        this.setY(y - HEIGHT);
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
            BufferedImage image = ImageIO.read(new File("src/main/resources/bullet.png"));
            g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void move() {
        // Move the Bullet based on the calculated angle
        this.setX(this.getX() + SPEED * Math.cos(angle));
        this.setY(this.getY() + SPEED * Math.sin(angle));
        ;
    }
}