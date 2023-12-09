package org.example.model;

import org.example.dto.*;
import org.example.view.ControllerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int score;
    private int countParachutistOnGround;

    private final List<Bullet> bullets;
    private final List<Parachutist> parachutists;
    private final List<Helicopter> helicopters;
    private final Gun gun;
    private final ControllerListener controllerListener;

    public Game(ControllerListener controllerListener, int widthGame, int heightGame, int gunWidth, int gunHeight) {
        score = 0;
        countParachutistOnGround = 0;
        this.controllerListener = controllerListener;
        gun = new Gun(widthGame / 2, heightGame, gunWidth, gunHeight);
        bullets = new ArrayList<>();
        parachutists = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public void updateGame(int heightGame, int heightSoldier) {
        moveObjects(heightGame, heightSoldier);
        IndicesReduced getIndicesReducedObjects = controllerListener.getIndicesReducedObjects();
        increaseScore(getIndicesReducedObjects.indicesHelicopters().size() + getIndicesReducedObjects.indicesParachutists().size());
        List<Integer> indicesBulletsToRemove = getIndicesReducedObjects.indicesBullets();
        List<Integer> indicesHelicoptersToRemove = getIndicesReducedObjects.indicesHelicopters();
        List<Integer> indicesParachutistsToRemove = getIndicesReducedObjects.indicesParachutists();
        for (int i = indicesBulletsToRemove.size() - 1; i >= 0; i--) {
            int deb=indicesBulletsToRemove.get(i);
            bullets.remove(deb);
        }
        for (int i = indicesHelicoptersToRemove.size() - 1; i >= 0; i--) {
            int deb=indicesHelicoptersToRemove.get(i);
            helicopters.remove(deb);
        }
        for (int i = indicesParachutistsToRemove.size() - 1; i >= 0; i--) {
            int deb=indicesParachutistsToRemove.get(i);
            parachutists.remove(deb);
        }
    }

    private void moveObjects(int heightGame, int heightSoldier) {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        for (Parachutist parachutist : parachutists) {
            parachutist.move(heightGame, heightSoldier);
            if (parachutist.getOnGround() == 1) {
                countParachutistOnGround++;
            }
        }
        for (Helicopter helicopter : helicopters) {
            helicopter.move();
        }
    }

    public void createParachutist(int widthParachutist, int heightParachutist) {
        int size = helicopters.size();
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            if (random.nextInt(100) < 15) {
                Parachutist parachutist = new Parachutist(helicopters.get(i).getX(), widthParachutist, heightParachutist);
                parachutists.add(parachutist);
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

    public int getCountParachutistOnGround() {
        return countParachutistOnGround;
    }

    public GameInfo toGameInfo() {
        List<Dto> dtos = new ArrayList<>();
        for (Bullet b : bullets) {
            dtos.add(new Dto(b.getX(), b.getY(), DtoType.BULLET));
        }
        for (Helicopter h : helicopters) {
            dtos.add(new Dto(h.getX(), h.getY(), DtoType.HELICOPTER, h.getDirection()));
        }
        for (Parachutist p : parachutists) {
            dtos.add(new Dto(p.getX(), p.getY(), DtoType.PARACHUTIST, p.getOnGround()));
        }
        dtos.add(new Dto(gun.getX(), gun.getY(), DtoType.GUN));

        return new GameInfo(dtos);
    }


}