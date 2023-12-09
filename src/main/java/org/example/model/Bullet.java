package org.example.model;
/*
public void drawBullet(Graphics g,Bullet bullet) {
    //private static final int WIDTH = 32;
    //private static final int HEIGHT = 32;
    try {
        BufferedImage image = ImageIO.read(new File("src/main/resources/bullet.png"));
        g.drawImage(image, (int) bullet.getX(), (int) bullet.getY(), null);
    } catch (IOException e) {
        e.printStackTrace();
    }
}*/

public final class Bullet extends GameObject {

    private static final int SPEED = 5;
    private double angle;

    public Bullet(int x, int y, double angle, int widthBullet, int heightBullet) {
        this.setX(x - widthBullet);
        this.setY(y - heightBullet);
        this.angle = angle;
    }


    public void move() {
        this.setX((int) (this.getX() + SPEED * Math.cos(angle)));
        this.setY((int) (this.getY() + SPEED * Math.sin(angle)));
    }
}