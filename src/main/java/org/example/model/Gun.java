package org.example.model;



public final class Gun extends GameObject {
    private static final int SHOOT_DELAY = 200;
    private double angle;
    private long lastShootTime;

    public Gun(int x, int y, int widthGun, int heightGun) {
        this.setX(x - widthGun);
        this.setY(y - heightGun);
        this.lastShootTime = System.currentTimeMillis();
    }

    public double getAngle() {
        return angle;
    }
    public void setAngle(int mouseX, int mouseY, int widthGun, int heightGun) {
        angle = Math.atan2(mouseY - this.getY() - heightGun / 2, mouseX - this.getX() - widthGun / 2);

    }

    public Bullet generateBullet(int widthGun, int heightGun, int widthBullet, int heightBullet) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > SHOOT_DELAY) {
            lastShootTime = currentTime;
            Bullet bullet = new Bullet(this.getX() + widthGun / 2, this.getY() + heightGun / 2, angle, widthBullet, heightBullet);
            return bullet;
        }
        return null;
    }
}