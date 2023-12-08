package org.example.view;


import org.example.dto.Dto;
import org.example.dto.GameInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class GamePanel extends JPanel {
    private Image backgroundImage;
    private GameInfo gameInfo;

    public GamePanel() {
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/gamebackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        for (Dto dto : gameInfo.dtos()) {
            switch (dto.dtoType()) {
                case GUN -> drawGun(g, dto.x(), dto.y());
                case HELICOPTER -> drawHelicopter(g, dto.x(), dto.additional());
                case PARACHUTIST -> drawParachutist(g, dto.x(), dto.y(), dto.additional());
                default -> drawBullet(g, dto.x(), dto.y());
            }
        }
        repaint();
    }

    private void drawGun(Graphics g, int x, int y) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/gunbase.png"));
            g.drawImage(image, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawHelicopter(Graphics g, int x, int direction) {
        try {
            BufferedImage image;
            if (direction == 1) {
                image = ImageIO.read(new File("src/main/resources/minihelicopter2.png"));
                g.drawImage(image, x, 0, null);
            } else {
                image = ImageIO.read(new File("src/main/resources/minihelicopter1.png"));
                g.drawImage(image, x, 0, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBullet(Graphics g, int x, int y) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/bullet.png"));
            g.drawImage(image, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawParachutist(Graphics g, int x, int y, int onGround) {
        try {
            if (onGround == 1) {
                BufferedImage image = ImageIO.read(new File("src/main/resources/soldier.png"));
                g.drawImage(image, x, y, null);
            } else {
                BufferedImage image = ImageIO.read(new File("src/main/resources/paratrooper.png"));
                g.drawImage(image, x, y, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
