package org.example.view;


import org.example.config.GameConfig;
import org.example.dto.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private Image backgroundImage;
    private GameInfo gameInfo;
    private static int gameWidth;
    private static int gameHeight;
    private static int bulletWidth;
    private static int bulletHeight;
    private static int paratrooperWidth;
    private static int paratrooperHeight;
    private static int helicopterWidth;
    private static int helicopterHeight;

    public GamePanel(GameConfig gameConfig) {
        gameWidth = gameConfig.getGameWidth();
        gameHeight = gameConfig.getGameHeight();
        bulletWidth = gameConfig.getBulletWidth();
        bulletHeight = gameConfig.getBulletHeight();
        paratrooperWidth = gameConfig.getParatrooperWidth();
        paratrooperHeight = gameConfig.getParatrooperHeight();
        helicopterWidth = gameConfig.getHelicopterWidth();
        helicopterHeight = gameConfig.getHelicopterHeight();
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
        if (gameInfo != null) {
            for (GameObjectInfo dto : gameInfo.dtos()) {
                switch (dto.type()) {
                    case GUN:
                        GunDto gunDto = (GunDto) dto;
                        drawGun(g, gunDto.x(), gunDto.y(), gunDto.angle());
                        break;
                    case HELICOPTER:
                        HelicopterDto helicopterDto = (HelicopterDto) dto;
                        drawHelicopter(g, helicopterDto.x(), helicopterDto.getDirection());
                        break;
                    case PARATROOPER:
                        ParatrooperDto paratrooperDto = (ParatrooperDto) dto;
                        drawParatrooper(g, paratrooperDto.x(), paratrooperDto.y(), paratrooperDto.onGround());
                        break;
                    default:
                        drawBullet(g, dto.x(), dto.y());
                }
            }
        }
        repaint();
    }

    private void drawGun(Graphics g, int x, int y, double angle) {
        int centerX = x, centerY = y;
        try {
            BufferedImage imageGun = ImageIO.read(new File("src/main/resources/gunbase.png"));
            centerX += imageGun.getWidth() / 2;
            centerY += imageGun.getHeight() / 2;
            g.drawImage(imageGun, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedImage imageBarrel = ImageIO.read(new File("src/main/resources/barrelgun.png"));
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform transform = new AffineTransform();
            transform.rotate(angle + 3.14 / 2, centerX, centerY);
            g2d.setTransform(transform);

            g2d.drawImage(imageBarrel, x, y, null);

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

    private void drawParatrooper(Graphics g, int x, int y, boolean onGround) {
        try {
            if (onGround) {
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
