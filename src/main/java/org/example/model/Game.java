package org.example.model;

import org.example.config.Config;
import org.example.dto.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private final List<Bullet> bullets;
    private final List<Paratrooper> paratroopers;
    private final List<Helicopter> helicopters;
    private final Gun gun;
    private final Config config;
    private final ScoreManager scoreManager;

    public Game(String name,Config config,ScoreManager scoreManager) {
        this.config = config;
        this.scoreManager = scoreManager;
        scoreManager.clearCurrent();
        scoreManager.setCurrentName(name);
        gun = new Gun(config.game().width() / 2, config.game().height(), config.gun().width(), config.gun().height());
        bullets = new ArrayList<>();
        paratroopers = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public boolean updateGame() {
        moveObjects();
        boolean isEnd = isEndGame();
        IndicesReduced getIndicesReducedObjects = getIndicesReducedObjects();
        scoreManager.addScore(getIndicesReducedObjects.indicesHelicopters().size() + getIndicesReducedObjects.indicesParatroopers().size());
        List<Integer> indicesBulletsToRemove = getIndicesReducedObjects.indicesBullets();
        List<Integer> indicesHelicoptersToRemove = getIndicesReducedObjects.indicesHelicopters();
        List<Integer> indicesParatroopersToRemove = getIndicesReducedObjects.indicesParatroopers();
        for (int i = indicesBulletsToRemove.size() - 1; i >= 0; i--) {
            int ind = indicesBulletsToRemove.get(i);
            bullets.remove(ind);
        }
        for (int i = indicesHelicoptersToRemove.size() - 1; i >= 0; i--) {
            helicopters.remove((int) indicesHelicoptersToRemove.get(i));
        }
        for (int i = indicesParatroopersToRemove.size() - 1; i >= 0; i--) {
            int ind = indicesParatroopersToRemove.get(i);
            paratroopers.remove(ind);
        }
        return isEnd;
    }

    private void moveObjects() {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        for (Paratrooper paratrooper : paratroopers) {
            paratrooper.move();
        }
        for (Helicopter helicopter : helicopters) {
            helicopter.move();
        }
    }

    private boolean isEndGame() {
        int countParatrooperOnGround = 0;
        for (Paratrooper paratrooper : paratroopers) {
            if (paratrooper.getOnGround()) {
                countParatrooperOnGround++;
                if (countParatrooperOnGround == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public void createParatrooper() {
        int size = helicopters.size();
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            if (random.nextInt(100) < 15) {
                Paratrooper paratrooper = new Paratrooper(helicopters.get(i).getX(), config.paratrooper().width(), config.paratrooper().height(), config.soldier().height(), config.game().height());
                paratroopers.add(paratrooper);
            }
        }
    }

    public void createHelicopter() {
        Helicopter helicopter = new Helicopter(config.game().width());
        helicopters.add(helicopter);
    }

    public void createBullet() {
        Bullet bullet = gun.generateBullet(config.gun().width(), config.gun().height(), config.barrel().height(), config.bullet().width(), config.bullet().height());
        if (bullet != null) {
            bullets.add(bullet);
        }
    }

    public void updateGun(int mouseX, int mouseY) {
        gun.setAngle(mouseX, mouseY, config.gun().width(), config.gun().height());
    }

    public List<Score> getScoresAndNames() {
        return scoreManager.getScoresAndNames();
    }

    public IndicesReduced getIndicesReducedObjects() {
        ArrayList<Integer> indicesRemovedBullets = new ArrayList<>();
        ArrayList<Integer> indicesRemovedHelicopters = new ArrayList<>();
        ArrayList<Integer> indicesRemovedParatroopers = new ArrayList<>();
        Rectangle screen = new Rectangle(0, 0, config.game().width(), config.game().height());
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), config.bullet().width(), config.bullet().height());
            for (int j = 0; j < helicopters.size(); j++) {
                Helicopter helicopter = helicopters.get(j);
                Rectangle helicopterRect = new Rectangle(helicopter.getX(), helicopter.getY(), config.helicopter().width(), config.helicopter().height());
                if (helicopterRect.intersects(bulletRect)) {
                    indicesRemovedBullets.add(i);
                    indicesRemovedHelicopters.add(j);
                }
                for (int k = 0; k < paratroopers.size(); k++) {
                    Paratrooper paratrooper = paratroopers.get(k);
                    Rectangle paratrooperRect = new Rectangle(paratrooper.getX(), paratrooper.getY(), config.paratrooper().width(), config.paratrooper().height());
                    if (paratrooperRect.intersects(bulletRect)) {
                        indicesRemovedBullets.add(i);
                        indicesRemovedParatroopers.add(k);
                    }
                    if (!bulletRect.intersects(screen)) {
                        indicesRemovedBullets.add(i);
                    }
                }
            }
        }
        for (int j = 0; j < helicopters.size(); j++) {
            Helicopter helicopter = helicopters.get(j);
            Rectangle helicopterRect = new Rectangle(helicopter.getX(), helicopter.getY(), config.helicopter().width(), config.helicopter().height());
            if (!helicopterRect.intersects(screen)) {
                indicesRemovedHelicopters.add(j);
            }
        }
        for (int k = 0; k < paratroopers.size(); k++) {
            Paratrooper paratrooper = paratroopers.get(k);
            Rectangle paratrooperRect = new Rectangle(paratrooper.getX(), paratrooper.getY(), config.paratrooper().width(), config.paratrooper().height());//paratrooperWidth, paratrooperHeight
            if (!paratrooperRect.intersects(screen)) {
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

    public GameInfo toGameInfo() {
        List<GameObjectInfo> dtos = new ArrayList<>();
        for (Bullet b : bullets) {
            dtos.add(new Dto(b.getX(), b.getY(), DtoType.BULLET));
        }
        for (Helicopter h : helicopters) {
            dtos.add(new HelicopterDto(h.getX(), h.getY(), h.getDirection()));
        }
        for (Paratrooper p : paratroopers) {
            dtos.add(new ParatrooperDto(p.getX(), p.getY(), p.getOnGround()));
        }
        dtos.add(new GunDto(gun.getX(), gun.getY(), gun.getAngle()));

        return new GameInfo(dtos);
    }


}