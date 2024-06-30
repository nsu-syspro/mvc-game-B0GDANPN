package org.example.model;

import java.util.Random;

public final class Helicopter extends GameObject {
    private static final int SPEED = 5;
    private final Direction direction;

    private static final Random RANDOM = new Random();

    public Helicopter(int widthGame) {
        this.direction = Direction.values()[RANDOM.nextInt(0, Direction.values().length)];
        this.setX(direction == Direction.LEFT ? widthGame - 1 : 0);
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void move() {
        this.setX(this.getX() + direction.delta(SPEED));
    }
}