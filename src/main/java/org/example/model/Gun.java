package org.example.model;


import static java.lang.Math.cos;
import static java.lang.Math.sin;

public final class Gun extends GameObject {
    private static final int SHOOT_DELAY = 200;
    private double angle;
    private long lastShootTime;

    public Gun(int x, int y, int widthGun, int heightGun) {
        this.setX(x - widthGun / 2);
        this.setY(y - heightGun);
        this.lastShootTime = System.currentTimeMillis();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(int mouseX, int mouseY, int widthGun, int heightGun) {
        angle = Math.atan2(mouseY - this.getY() - (double) heightGun / 2, mouseX - this.getX() - (double) widthGun / 2);

    }

    public Bullet generateBullet(int widthGun, int heightGun, int widthBurrel,int heightBurrel, int widthBullet, int heightBullet) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > SHOOT_DELAY) {
            lastShootTime = currentTime;
            double centerGunX = this.getX() + 0.5*widthGun;
            double centerGunY = this.getY() + 0.5*heightGun;
            double dx=0;
            double dy=-heightBurrel;
            double angle = this.getAngle()+3.14/2;
            double new_dx = dx * cos(angle) - dy * sin(angle);
            double new_dy = dx * sin(angle) + dy * cos(angle);
            double new_x = centerGunX + new_dx;
            double new_y = centerGunY + new_dy;
            return new Bullet((int) new_x, (int)new_y, this.getAngle(), widthBullet, heightBullet);

            //return new Bullet(this.getX() + widthGun / 2, this.getY() + heightGun / 2, angle, widthBullet, heightBullet);
        }
        return null;
    }
}