package org.example.view;


import org.example.dto.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePanel extends JPanel {
    private Image backgroundImage;
    private GameInfo gameInfo;
    private static final int gameWidth = 1200;
    private static final int gameHeight = 800;
    private static final int bulletWidth = 32;
    private static final int bulletHeight = 32;
    private static final int paratrooperWidth = 72;
    private static final int paratrooperHeight = 90;
    private static final int helicopterWidth = 200;
    private static final int helicopterHeight = 95;

    private final List<Integer> indicesBullet;
    private final List<Integer> indicesParatroopers;
    private final List<Integer> indicesHelicopters;

    public GamePanel() {
        indicesBullet = new ArrayList<>();
        indicesParatroopers = new ArrayList<>();
        indicesHelicopters = new ArrayList<>();
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/gamebackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameInfo(GameInfo gameInfo) {
        indicesBullet.clear();
        indicesParatroopers.clear();
        indicesHelicopters.clear();
        this.gameInfo = gameInfo;
        for (int i = 0; i < gameInfo.dtos().size(); i++) {
            if (gameInfo.dtos().get(i).dtoType() == DtoType.BULLET) {
                indicesBullet.add(i);
            } else if (gameInfo.dtos().get(i).dtoType() == DtoType.HELICOPTER) {
                indicesHelicopters.add(i);
            } else if (gameInfo.dtos().get(i).dtoType() == DtoType.PARACHUTIST && gameInfo.dtos().get(i).additional() == 0) {
                indicesParatroopers.add(i);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        if (gameInfo != null) {
            for (Dto dto : gameInfo.dtos()) {
                switch (dto.dtoType()) {
                    case GUN -> drawGun(g, dto.x(), dto.y());
                    case HELICOPTER -> drawHelicopter(g, dto.x(), dto.additional());
                    case PARACHUTIST -> drawParatrooper(g, dto.x(), dto.y(), dto.additional());
                    default -> drawBullet(g, dto.x(), dto.y());
                }
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

    private void drawParatrooper(Graphics g, int x, int y, int onGround) {
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

    public IndicesReduced getIndicesReducedObjects() {
        ArrayList<Integer> indicesRemovedBullets = new ArrayList<>();
        ArrayList<Integer> indicesRemovedHelicopters = new ArrayList<>();
        ArrayList<Integer> indicesRemovedParatroopers = new ArrayList<>();
        Rectangle screen = new Rectangle(0, 0, gameWidth, gameHeight);
        for (int i = 0; i < indicesBullet.size(); i++) {
            Dto dtoBullet = gameInfo.dtos().get(indicesBullet.get(i));
            Rectangle bullet = new Rectangle(dtoBullet.x(), dtoBullet.y(), bulletWidth, bulletHeight);
            for (int j = 0; j < indicesHelicopters.size(); j++) {
                Dto dtoHelicopter = gameInfo.dtos().get(indicesHelicopters.get(j));
                Rectangle helicopter = new Rectangle(dtoHelicopter.x(), dtoHelicopter.y(), helicopterWidth, helicopterHeight);
                if (helicopter.intersects(bullet)) {
                    indicesRemovedBullets.add(i);
                    indicesRemovedHelicopters.add(j);
                }
                for (int k = 0; k < indicesParatroopers.size(); k++) {
                    Dto dtoParatrooper = gameInfo.dtos().get(indicesParatroopers.get(k));
                    Rectangle paratrooper = new Rectangle(dtoParatrooper.x(), dtoParatrooper.y(), paratrooperWidth, paratrooperHeight);
                    if (paratrooper.intersects(bullet)) {
                        indicesRemovedBullets.add(i);
                        indicesRemovedParatroopers.add(k);
                    }
                    if (!bullet.intersects(screen)) {
                        indicesRemovedBullets.add(i);
                    }
                }
            }
        }
        for (int j = 0; j < indicesHelicopters.size(); j++) {
            Dto dtoHelicopter = gameInfo.dtos().get(indicesHelicopters.get(j));
            Rectangle helicopter = new Rectangle(dtoHelicopter.x(), dtoHelicopter.y(), helicopterWidth, helicopterHeight);
            if (!helicopter.intersects(screen)) {
                indicesRemovedHelicopters.add(j);
            }
        }
        for (int k = 0; k < indicesParatroopers.size(); k++) {
            Dto dtoParatrooper = gameInfo.dtos().get(indicesParatroopers.get(k));
            Rectangle paratrooper = new Rectangle(dtoParatrooper.x(), dtoParatrooper.y(), paratrooperWidth, paratrooperHeight);
            if (!paratrooper.intersects(screen)) {
                indicesRemovedParatroopers.add(k);
            }
        }
        Collections.sort(indicesRemovedBullets);
        int sizeRemovedBullets = indicesRemovedBullets.size();
        for (int i = 1; i < sizeRemovedBullets; i++) {
            if (indicesRemovedBullets.get(i) == indicesRemovedBullets.get(i - 1)) {
                indicesRemovedBullets.remove(i);
                i--;
                sizeRemovedBullets--;
            }
        }
        Collections.sort(indicesRemovedHelicopters);
        int sizeRemovedHelicopters = indicesRemovedHelicopters.size();
        for (int i = 1; i < sizeRemovedHelicopters; i++) {
            if (indicesRemovedHelicopters.get(i) == indicesRemovedHelicopters.get(i - 1)) {
                indicesRemovedHelicopters.remove(i);
                i--;
                sizeRemovedHelicopters--;
            }
        }
        Collections.sort(indicesRemovedParatroopers);
        int sizeRemovedParatroopers = indicesRemovedParatroopers.size();
        for (int i = 1; i < sizeRemovedParatroopers; i++) {
            if (indicesRemovedParatroopers.get(i) == indicesRemovedParatroopers.get(i - 1)) {
                indicesRemovedParatroopers.remove(i);
                i--;
                sizeRemovedParatroopers--;
            }
        }
        return new IndicesReduced(indicesRemovedBullets, indicesRemovedHelicopters, indicesRemovedParatroopers);
    }
}
