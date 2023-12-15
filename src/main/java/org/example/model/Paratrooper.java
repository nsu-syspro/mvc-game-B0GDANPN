package org.example.model;


public final class Paratrooper extends GameObject {
    private final int gameHeight;
    private final int soldatHeight;
    private boolean onGround = false;
    private static final int SPEED = 2;

    public void setOnGround() {
        this.onGround = true;

    }

    public int getOnGround() {
        return onGround ? 1 : 0;
    }


    public Paratrooper(int x, int paratrooperWidth, int paratrooperHeight, int soldatHeight, int gameHeight) {
        this.gameHeight = gameHeight;
        this.soldatHeight = soldatHeight;
        this.setX(x - paratrooperWidth / 2);
        this.setY(paratrooperHeight / 2);
    }

    @Override
    public void move() {
        if (!onGround) {
            this.setY(this.getY() + SPEED);
        }
        if (this.getY() >= gameHeight - 0.5 * soldatHeight) {
            this.setOnGround();
            this.setY((int) (gameHeight - 1.5 * soldatHeight));
        }
    }

}