package org.example.model;

import org.example.config.Config;
import org.example.dto.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final List<Bullet> bullets;
    private final List<Paratrooper> paratroopers;
    private final List<Helicopter> helicopters;
    private final Gun gun;
    private final Config config;
    private final String name;
    private int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Game(String name, Config config) {
        this.name = name;
        this.score = 0;
        this.config = config;
        gun = new Gun(config.game().width() / 2, config.game().height(), config.gun().width(), config.gun().height());
        bullets = new ArrayList<>();
        paratroopers = new ArrayList<>();
        helicopters = new ArrayList<>();
    }

    public boolean updateGame() {
        moveObjects();
        boolean isEnd = isEndGame();
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Paratrooper> paratroopersToRemove = new ArrayList<>();
        List<Helicopter> helicoptersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            for (Helicopter helicopter : helicopters) {
                if (intersects(helicopter.getX(), helicopter.getY(), config.helicopter().width(), config.helicopter().height(),
                        bullet.getX(), bullet.getY(), config.bullet().width(), config.bullet().height())) {
                    bulletsToRemove.add(bullet);
                    helicoptersToRemove.add(helicopter);
                }
            }
            for (Paratrooper paratrooper : paratroopers) {
                if (intersects(paratrooper.getX(), paratrooper.getY(), config.paratrooper().width(), config.paratrooper().height(),
                        bullet.getX(), bullet.getY(), config.bullet().width(), config.bullet().height())) {
                    bulletsToRemove.add(bullet);
                    paratroopersToRemove.add(paratrooper);
                }
            }

            if (!intersects(bullet.getX(), bullet.getY(), config.bullet().width(), config.bullet().height(),
                    0, 0, config.game().width(), config.game().height())) {
                bulletsToRemove.add(bullet);
            }

        }
        for (Helicopter helicopter : helicopters) {
            if (!intersects(helicopter.getX(), helicopter.getY(), config.helicopter().width(), config.helicopter().height(),
                    0, 0, config.game().width(), config.game().height())) {
                helicoptersToRemove.add(helicopter);
            }
        }
        for (Paratrooper paratrooper : paratroopers) {
            if (!intersects(paratrooper.getX(), paratrooper.getY(), config.paratrooper().width(), config.paratrooper().height(),
                    0, 0, config.game().width(), config.game().height())) {
                paratroopersToRemove.add(paratrooper);
            }
        }
        int oldHelicoptersSize = helicopters.size();
        int oldParatroopersSize = paratroopers.size();
        bullets.removeAll(bulletsToRemove);
        paratroopers.removeAll(paratroopersToRemove);
        helicopters.removeAll(helicoptersToRemove);
        int newHelicoptersSize = helicopters.size();
        int newParatroopersSize = paratroopers.size();
        this.score += oldHelicoptersSize - newHelicoptersSize + oldParatroopersSize - newParatroopersSize;
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

    public boolean createParatrooper() {
        boolean isCreated = false;
        int size = helicopters.size();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(100) < 15) {
                Paratrooper paratrooper = new Paratrooper(helicopters.get(i).getX(), config.paratrooper().width(), config.paratrooper().height(), config.soldier().height(), config.game().height());
                paratroopers.add(paratrooper);
                isCreated = true;
            }
        }
        return isCreated;
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
        gun.setAngle(mouseX-gun.getX()-(double)config.gun().width()/2, mouseY-gun.getY()-(double)config.gun().height()/2);
    }

    private boolean intersects(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
        Rectangle rectangle1 = new Rectangle(x1, y1, width1, height1);
        Rectangle rectangle2 = new Rectangle(x2, y2, width2, height2);
        return rectangle1.intersects(rectangle2);
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

    public int getGroundedParatrooperCount() {
        int count = 0;
        for (Paratrooper paratrooper : paratroopers) {
            if (paratrooper.getOnGround()) {
                count++;
            }
        }
        return count;
    }

    public void directlyCreateParatrooper(int x, int y) {
        Paratrooper paratrooper = new Paratrooper(0, config.paratrooper().width(), config.paratrooper().height(), config.soldier().height(), config.game().height());
        paratrooper.setX(x);
        paratrooper.setY(y);
        paratroopers.add(paratrooper);
    }

    public void directlyCreateBullet(int x, int y,double angle) {
        Bullet bullet = new Bullet(x, y, angle, config.bullet().width(), config.bullet().height());
        bullets.add(bullet);
    }
    public void directlyCreateHelicopter(int x) {
        Helicopter helicopter = new Helicopter(x);
        helicopter.setX(x);
        helicopters.add(helicopter);
    }

    public int getCountObjects() {//gun + others
        return 1 + helicopters.size() + paratroopers.size() + bullets.size();
    }
    public Bullet getLastBullet(){
        if (!bullets.isEmpty()){
            return bullets.getLast();
        }
        return null;
    }
    public Paratrooper getLastParatrooper(){
        if (!paratroopers.isEmpty()){
            return paratroopers.getLast();
        }
        return null;
    }
    public Helicopter getLastHelicopter(){
        if (!helicopters.isEmpty()){
            return helicopters.getLast();
        }
        return null;
    }
    public boolean wasEnd(){
        return isEndGame();
    }
}