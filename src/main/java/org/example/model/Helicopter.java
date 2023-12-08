package org.example.model;

import java.util.Random;
public final class Helicopter extends GameObject {
    private static final int SPEED = 3;
    private int x;
    private final int DIRECTION;

    public Helicopter(int widthGame, boolean vector) {
        if (vector) {
            this.setX(widthGame);
        } else {
            this.setX(0);
        }
        this.DIRECTION = vector ? -1 : 1;

    }

    public int getX() {
        return x;
    }

    public int getDirection() {
        return DIRECTION;
    }

    public void setX(int x) {
        this.x = x;
    }

    Parachutist createParatrooper(int helicopterWidth,int gameWidth,int gameHeight) {
        Random random = new Random();
        if (random.nextInt(100) < 15) {
            Parachutist parachutist = new Parachutist(this.getX() + helicopterWidth / 2, gameWidth, gameHeight);
            return parachutist;

        }
        return null;
    }

    public void move() {
        this.setX(this.getX() + SPEED * DIRECTION);
    }
}