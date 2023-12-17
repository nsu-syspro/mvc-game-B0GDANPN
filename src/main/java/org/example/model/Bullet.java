package org.example.model;

public final class Bullet extends GameObject {

    private static final int SPEED = 7;
    private final double angle;

    public Bullet(int x, int y, double angle, int widthBullet, int heightBullet) {
        this.setX(x - widthBullet);
        this.setY(y - heightBullet);
        this.angle = angle;
    }


    @Override
    public void move() {
        this.setX((int) (this.getX() + SPEED * Math.cos(angle)));
        this.setY((int) (this.getY() + SPEED * Math.sin(angle)));

    }
}