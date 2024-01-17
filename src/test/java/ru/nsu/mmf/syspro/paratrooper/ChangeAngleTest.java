package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Bullet;
import org.example.model.Game;
import org.junit.Test;

import java.awt.*;

public class ChangeAngleTest {
    Config gameConfig = Config.create();

    @Test
    public void checkRight() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(682, 720);//оружие вправо
        game.createBullet();
        Bullet bullet = game.getLastBullet();
        Rectangle excitedLocation = new Rectangle(682, 710, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bullet.getX(), bullet.getY(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkUp() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(567, 597);//оружие вверх
        game.createBullet();
        Bullet bullet = game.getLastBullet();
        Rectangle excitedLocation = new Rectangle(567, 597, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bullet.getX(), bullet.getY(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkLeft() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(454, 713);//оружие влево
        game.createBullet();
        Bullet bullet = game.getLastBullet();
        Rectangle excitedLocation = new Rectangle(454, 713, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bullet.getX(), bullet.getY(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assert excitedLocation.intersects(realLocation);
    }
}
