package org.example.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Model {
    static class Helicopter {
        private  int x;
        private final Direction direction;
        private final int speed;

        Paratrooper createParatrooper() {
             if (Math.random() > fsdfsd) {

             }
             return null;
        }

        void move() {

        }
    }

    static class Paratrooper {
        private int x;
        private int y;

        // TODO: what to do when readched end

        void move() {

        }
    }

    static class Gun {

        private int angle;

        Bullet generateBullet() {

        }

        // TOD: params??
        void changeAngle() {

        }
    }

    static class Bullet {
        int x;
        int y;
        private final int angle;

        void move() {

        }
    }

    static class Game {

        private int nParatroopersReachedGround;

        private final List<Bullet> bullets;
        private final List<Paratrooper> paratroopers;
        private final List<Helicopter> helicopters;
        private final Gun gun;

        void update(int gunAngle) {
            List<Bullet> bulletsToRemove = new ArrayList<>();
            for (Bullet bullet : bullets) {
                Paratrooper paratrooper = collidesWith(bullet);
                if (paratrooper != null) {
                    paratroopers.remove(paratrooper);
                    bulletsToRemove.add(bullet);
                    continue;
                }
            }
            gun.changeAngle(gunAngle);
        }

        void shoot() {
            bullets.add(gun.generateBullet());
        }


        Dto.GameInfo toGameInfo() {
            List<Dto> dtos = new ArrayList<>();
            for (Bullet b : bullets) {
                dtos.add(new Dto(b.x, b.y, Dto.DtoType.BULLET));
            }
            // TODO: rest of the objects


            return new Dto.GameInfo(dtos, nParatroopersReachedGround, gun.angle);
        }


    }

}

class Controller {
    org.example.model.Model.Game game;
    View view;

    Timer gameTimer = new Timer(100, e -> {
        int angle = view.getGunAngle();
        game.update(angle);
        Dto.GameInfo gameInfo = game.toGameInfo();
        view.setGameInfo(gameInfo);
    });

    void shoot() {
        game.shoot();
    }

}

class View {}


record Dto(int x, int y, DtoType dtoType) {


    enum DtoType {
        GUN,
        PARATROOPER,
        HELICOPTER,
        BULLET
    }

    record GameInfo(List<Dto> dtos, int nParatroopersOnTheGround, int gunAngle) {

    }

}
