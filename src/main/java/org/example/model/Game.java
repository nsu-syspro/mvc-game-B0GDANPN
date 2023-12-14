package org.example.model;

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

    public Game(ControllerListener controllerListener, int widthGame, int heightGame, int gunWidth, int gunHeight) {
        score = 0;
        this.controllerListener = controllerListener;
        gun = new Gun(widthGame / 2, heightGame, gunWidth, gunHeight);
        bullets = new ArrayList<>();
        paratroopers = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public void updateGame(int heightGame, int heightSoldier) {
        moveObjects(heightGame, heightSoldier);
        // CR: move to model
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
            helicopters.remove((int)indicesHelicoptersToRemove.get(i));
        }
        for (int i = indicesParatroopersToRemove.size() - 1; i >= 0; i--) {
            int ind = indicesParatroopersToRemove.get(i);
            paratroopers.remove(ind);
        }
    }

    private void moveObjects(int heightGame, int heightSoldier) {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        int countParatrooperOnGround = 0;
        for (Paratrooper paratrooper : paratroopers) {
            paratrooper.move(heightGame, heightSoldier);
            if (paratrooper.getOnGround() == 1) {
                countParatrooperOnGround++;
                if (countParatrooperOnGround >= 5) {
                    controllerListener.endGame();
                }
            }
        }
        for (Helicopter helicopter : helicopters) {
            helicopter.move();
        }
    }

    public void createParatrooper(int widthParatrooper, int heightParatrooper) {
        int size = helicopters.size();
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            if (random.nextInt(100) < 15) {
                Paratrooper paratrooper = new Paratrooper(helicopters.get(i).getX(), widthParatrooper, heightParatrooper);
                paratroopers.add(paratrooper);
            }
        }
    }

    public void createHelicopter(int widthScreen) {
        Random random = new Random();
        boolean vector = random.nextBoolean();

        Helicopter helicopter = new Helicopter(widthScreen, vector);
        helicopters.add(helicopter);
    }

    public void createBullet(int widthGun, int heightGun, int widthBullet, int heightBullet) {
        Bullet bullet = gun.generateBullet(widthGun, heightGun, widthBullet, heightBullet);
        if (bullet != null) {
            bullets.add(bullet);
        }
    }


    public void updateGun(int mouseX, int mouseY, int widthGun, int heightGun) {
        gun.setAngle(mouseX, mouseY, widthGun, heightGun);
    }

    public void increaseScore(int difference) {
        score += difference;
    }

    public int getScore() {
        return score;
    }

    public GameInfo toGameInfo() {
        List<Dto> dtos = new ArrayList<>();
        for (Bullet b : bullets) {
            dtos.add(new Dto(b.getX(), b.getY(), DtoType.BULLET));
        }
        for (Helicopter h : helicopters) {
            dtos.add(new Dto(h.getX(), h.getY(), DtoType.HELICOPTER, h.getDirection()));
        }
        for (Paratrooper p : paratroopers) {
            dtos.add(new Dto(p.getX(), p.getY(), DtoType.PARATROOPER, p.getOnGround()));
        }
        dtos.add(new Dto(gun.getX(), gun.getY(), DtoType.GUN));

        return new GameInfo(dtos);
    }


}