package org.example.view;


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

    public GamePanel() {
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/gamebackground.png"));
        } catch (IOException e) {
            System.out.println("Can`t open game backround theme");
            System.exit(0);
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
            System.out.println("Can`t open gun image");
            System.exit(0);
        }
        try {
            BufferedImage imageBarrel = ImageIO.read(new File("src/main/resources/barrelgun.png"));
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform transform = new AffineTransform();
            transform.rotate(angle + 3.14 / 2, centerX, centerY);
            g2d.setTransform(transform);

            g2d.drawImage(imageBarrel, x, y, null);

        } catch (IOException e) {
            System.out.println("Can`t open barrel gun image");
            System.exit(0);
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
            System.out.println("Can`t open helicopter image");
            System.exit(0);
        }
    }

    private void drawBullet(Graphics g, int x, int y) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/bullet.png"));
            g.drawImage(image, x, y, null);
        } catch (IOException e) {
            System.out.println("Can`t open bullet image");
            System.exit(0);
        }
    }

    private void drawParatrooper(Graphics g, int x, int y, boolean onGround) {
        try {
            BufferedImage image;
            if (onGround) {
                image = ImageIO.read(new File("src/main/resources/soldier.png"));
            } else {
                image = ImageIO.read(new File("src/main/resources/paratrooper.png"));
            }
            g.drawImage(image, x, y, null);
        } catch (IOException e) {
            System.out.println("Can`t open soldier or paratrooper image");
            System.exit(0);
        }
    }
}
