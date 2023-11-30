package org.example.model;

import org.example.dto.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Parachutist extends GameObject {
    private static final int WIDTH = 72;
    private static final int HEIGHT = 90;
    private static final int SPEED = 2;

    public Parachutist(double x, double y) {
        this.setX(x - WIDTH / 2);
        this.setY(HEIGHT / 2);
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
            BufferedImage image = ImageIO.read(new File("resources/paratrooper.png"));
            g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        this.setY(this.getY() + SPEED);
    }
}