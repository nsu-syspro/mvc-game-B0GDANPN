package org.example.model;

import java.util.Random;

public final class Helicopter extends GameObject {
    private static final int SPEED = 3;
    private int x;
    // CR: enum
    private final Direction direction;

    private static final Random RANDOM = new Random();

    public Helicopter(int widthGame) {
        this.direction = Direction.values()[RANDOM.nextInt(0, Direction.values().length)];
        this.x = direction == Direction.LEFT ? widthGame - 1 : 0;
    }

    public int getX() {
        return x;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void move() {
        this.setX(this.getX() + direction.delta(SPEED));
    }
}