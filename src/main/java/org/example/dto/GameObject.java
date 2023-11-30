package org.example.dto;

import java.awt.Graphics;
import java.awt.Rectangle;

public class GameObject {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void move() {

    }

    public Rectangle getBounds() {
        return new Rectangle((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
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