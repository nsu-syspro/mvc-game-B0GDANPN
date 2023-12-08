package org.example.model;

public sealed class GameObject permits Bullet,Parachutist,Helicopter,Gun{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int x) {
        this.y = y;
    }
}