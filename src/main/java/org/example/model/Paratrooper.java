package org.example.model;


public final class Paratrooper extends GameObject {
    private boolean onGround = false;
    private static final int SPEED = 2;

    public void setOnGround() {
        this.onGround = true;

    }

    public int getOnGround() {
        return onGround ? 1 : 0;
    }


    public Paratrooper(int x, int paratrooperWidth, int paratrooperHeight) {
        this.setX(x - paratrooperWidth / 2);
        this.setY(paratrooperHeight / 2);
    }

    public void move(int gameHeight, int soldatHeight) {
        if (!onGround) {
            this.setY(this.getY() + SPEED);
        }
        if (this.getY() >= gameHeight - 0.5 * soldatHeight) {
            this.setOnGround();
            this.setY((int) (gameHeight - 1.5 * soldatHeight));
        }
    }

}