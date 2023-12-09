package org.example.model;

/*
public void drawParachutist(Graphics g, Parachutist parachutist) {
    //private static final int WIDTH = 72;
    //private static final int HEIGHT = 90;
    try {
        BufferedImage image = ImageIO.read(new File("src/main/resources/paratrooper.png"));
        g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void drawSoldier(Graphics g) {
    //private static final int WIDTH = 72;
    //private static final int HEIGHT = 72;
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/soldier.png"));
            g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

public final class Paratrooper extends GameObject {
    private boolean onGround = false;
    private static final int SPEED = 2;

    public void setOnGround() {
        this.onGround = true;

    }

    public int getOnGround() {
        return onGround ? 1 : 0;
    }


    public Paratrooper(int x, int parachutistWidth, int parachutistHeight) {
        this.setX(x - parachutistWidth / 2);
        this.setY(parachutistHeight / 2);
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