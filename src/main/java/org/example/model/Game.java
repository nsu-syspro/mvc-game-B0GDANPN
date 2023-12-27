package org.example.model;

import org.example.config.GameConfig;
import org.example.dto.*;
import org.example.view.ControllerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int score;

    private final List<Bullet> bullets;
    private final List<Paratrooper> paratroopers;
    private final List<Helicopter> helicopters;
    private final Gun gun;
    private final ControllerListener controllerListener;
    private final GameConfig gameConfig;

    public Game(ControllerListener controllerListener, GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        score = 0;
        this.controllerListener = controllerListener;
        gun = new Gun(gameConfig.getGameWidth() / 2, gameConfig.getGameHeight(), gameConfig.getGunWidth(), gameConfig.getGunHeight());
        bullets = new ArrayList<>();
        paratroopers = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public boolean updateGame() {
        moveObjects();
        boolean isEnd=isEndGame();
        IndicesReduced getIndicesReducedObjects = controllerListener.getIndicesReducedObjects();
        increaseScore(getIndicesReducedObjects.indicesHelicopters().size() + getIndicesReducedObjects.indicesParatroopers().size());
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
                Paratrooper paratrooper = new Paratrooper(helicopters.get(i).getX(), gameConfig.getParatrooperWidth(), gameConfig.getParatrooperHeight(), gameConfig.getSoldierHeight(), gameConfig.getGameHeight());
                paratroopers.add(paratrooper);
            }
        }
    }

    public void createHelicopter() {
        Helicopter helicopter = new Helicopter(gameConfig.getGameWidth());
        helicopters.add(helicopter);
    }

    public void createBullet() {
        Bullet bullet = gun.generateBullet(gameConfig.getGunWidth(), gameConfig.getGunHeight(),gameConfig.getBarrelHeight(), gameConfig.getBulletWidth(), gameConfig.getBulletHeight());
        if (bullet != null) {
            bullets.add(bullet);
        }
    }

    public void updateGun(int mouseX, int mouseY) {
        gun.setAngle(mouseX, mouseY, gameConfig.getGunWidth(), gameConfig.getGunHeight());
    }

    public void increaseScore(int difference) {
        score += difference;
    }

    public int getScore() {
        return score;
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