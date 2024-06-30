package org.example.model;


public final class Paratrooper extends GameObject {
    private final int gameHeight;
    private final int soldierHeight;
    private boolean onGround = false;
    private static final int SPEED = 2;

    public void setOnGround() {
        this.onGround = true;

    }

    public boolean getOnGround() {
        return onGround;
    }


    public Paratrooper(int x, int paratrooperHeight, int soldierHeight, int gameHeight) {
        this.gameHeight = gameHeight;
        this.soldierHeight = soldierHeight;
        this.setX(x);
        this.setY(paratrooperHeight / 2);
    }

    @Override
    public void move() {
        if (!onGround) {
            this.setY(this.getY() + SPEED);
        }
        if (this.getY() >= gameHeight - 1.5 * soldierHeight) {
            this.setOnGround();
            this.setY((int) (gameHeight - 1.5 * soldierHeight));
        }
    }

}