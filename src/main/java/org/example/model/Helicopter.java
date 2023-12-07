package org.example.model;

import org.example.dto.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Helicopter extends GameObject {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 95;
    private static final int SPEED = 3;
    private int VECTOR;

    public Helicopter(int widthScreen, boolean vector) {
        this.setY(0);
        if (vector) {
            this.setX(widthScreen);
        } else {
            this.setX(0);
        }
        this.VECTOR = vector ? -1 : 1;

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
            if (VECTOR == 1) {
                image = ImageIO.read(new File("src/main/resources/minihelicopter2.png"));
                g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
            } else {
                image = ImageIO.read(new File("src/main/resources/minihelicopter1.png"));
                g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Draw the helicopter image here
    }

    public void move() {
        this.setX(this.getX() + SPEED * VECTOR);
    }
}