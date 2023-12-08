package org.example.model;

import org.example.dto.*;
import org.example.view.ControllerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int score;
    private int nParatroopersReachedGround;

    private final List<Bullet> bullets;
    private final List<Parachutist> parachutists;
    private final List<Helicopter> helicopters;
    private final Gun gun;
    private final ControllerListener controllerListener;

    public Game(ControllerListener controllerListener,int widthGame, int heightGame, int gunWidth, int gunHeight) {
        score = 0;
        nParatroopersReachedGround = 0;
        this.controllerListener = controllerListener;
        gun = new Gun(widthGame / 2, heightGame, gunWidth, gunHeight);
        bullets = new ArrayList<>();
        parachutists = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public void updateGame() {
        List<Bullet> bulletsToRemove = controllerListener.getIntersectedBullets();
        List<Parachutist> parachutistsToRemove = controllerListener.getIntersectedBullets();
        List<Helicopter> helicoptersToRemove = controllerListener.getIntersectedBullets();
        bullets.removeAll(bulletsToRemove);
        helicopters.removeAll(helicoptersToRemove);
        parachutists.removeAll(parachutistsToRemove);
    }

    public void createParachutist(int widthParachutist, int heightParachutist) {
        int size = helicopters.size();
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            if (random.nextInt(100) < 20) {
                 Parachutist parachutist = new Parachutist(helicopters.get(i).getX(),widthParachutist, heightParachutist);
                 if (parachutist != null) {
                     parachutists.add(parachutist);
                 }
            }
        }
    }
    public void createHelicopter(int widthScreen) {
        Random random = new Random();
        boolean vector = random.nextBoolean();
        Helicopter helicopter = new Helicopter(widthScreen, vector);
        if (helicopter != null) {
            helicopters.add(helicopter);
        }
    }

    public void createBullet(int widthGun, int heightGun, int widthBullet, int heightBullet) {
        bullets.add(gun.generateBullet(widthGun, heightGun, widthBullet, heightBullet));
    }
    public void updateGun(int mouseX, int mouseY,int widthGun, int heightGun) {
        gun.setAngle(mouseX,mouseY,widthGun,heightGun);
    }

    public void increaseScore() {
        score++;
    }


    public GameInfo toGameInfo() {
        List<Dto> dtos = new ArrayList<>();
        for (Bullet b : bullets) {
            dtos.add(new Dto(b.getX(), b.getY(), DtoType.BULLET));
        }
        for (Helicopter h : helicopters) {
            dtos.add(new Dto(h.getX(), h.getY(), DtoType.HELICOPTER,h.getDirection()));
        }
        for (Parachutist p : parachutists) {
            dtos.add(new Dto(p.getX(), p.getY(), DtoType.PARACHUTIST,p.getOnGround()));
        }
        dtos.add(new Dto(gun.getX(), gun.getY(), DtoType.GUN));

        return new GameInfo(dtos);
    }


}