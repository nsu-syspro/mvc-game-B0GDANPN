package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.*;
import org.junit.Test;

import java.awt.*;

import static java.lang.Math.PI;

public class MovingTest {
    Config gameConfig = Config.create();

    @Test
    public void checkMoveBullet() {
        Game game = new Game("movingTest", gameConfig);
        game.directlyCreateBullet(600+ gameConfig.bullet().width(), 400+ gameConfig.bullet().width(), PI/2);
        for (int i = 0; i < 10; i++) {
            game.updateGame();
        }
        Bullet bullet = game.getLastBullet();
        Rectangle excitedLocation = new Rectangle(600, 490, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bullet.getX(), bullet.getY(), gameConfig.bullet().width(), gameConfig.bullet().height());

        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkMoveParatrooper() {
        Game game = new Game("movingTest", gameConfig);
        game.directlyCreateParatrooper(600, 100);
        for (int i = 0; i < 50; i++) {
            game.updateGame();
        }
        Paratrooper paratrooper = game.getLastParatrooper();
        Rectangle excitedLocation = new Rectangle(600, 200, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(paratrooper.getX(), paratrooper.getY(), gameConfig.paratrooper().width(), gameConfig.paratrooper().height());

        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkMoveHelicopter() {
        Game game = new Game("movingTest", gameConfig);
        game.directlyCreateHelicopter(600);
        for (int i = 0; i < 50; i++) {
            game.updateGame();
        }
        Helicopter helicopter = game.getLastHelicopter();
        Rectangle excitedLocation;
        if (helicopter.getDirection()== Direction.RIGHT){
            excitedLocation = new Rectangle(850, 0, gameConfig.helicopter().width(), gameConfig.helicopter().height());
        }
        else{
            excitedLocation = new Rectangle(350, 0, gameConfig.helicopter().width(), gameConfig.helicopter().height());
        }
        Rectangle realLocation = new Rectangle(helicopter.getX(), helicopter.getY(), gameConfig.helicopter().width(), gameConfig.helicopter().height());
        assert excitedLocation.intersects(realLocation);
    }
}
