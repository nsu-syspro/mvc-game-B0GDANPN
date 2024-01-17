package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.dto.BulletDto;
import org.example.dto.HelicopterDto;
import org.example.dto.ParatrooperDto;
import org.example.model.Direction;
import org.example.model.Game;
import org.junit.Test;

import java.awt.*;

import static ru.nsu.mmf.syspro.paratrooper.ChangeAngleTest.*;


public class MovingTest {
    Config gameConfig = Config.create();

    @Test
    public void checkMoveBullet() throws InterruptedException {
        Game game = new Game("movingTest", gameConfig);
        game.updateGun(615, 615);
        Thread.sleep(500);
        game.createBullet();
        for (int i = 0; i < 10; i++) {
            game.updateGame();
        }
        BulletDto bullet = lastBullet(game);
        Rectangle excitedLocation = new Rectangle(570, 490, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bullet.x(), bullet.y(), gameConfig.bullet().width(), gameConfig.bullet().height());

        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkMoveParatrooper() {
        Game game = new Game("movingTest", gameConfig);
        game.createHelicopter();
        boolean wasCreated = false;
        while (!wasCreated) {
            wasCreated = game.createParatrooper();
        }
        int newHeight=lastParatrooper(game).y()+100;
        for (int i = 0; i < 50; i++) {
            game.updateGame();
        }
        ParatrooperDto paratrooper = lastParatrooper(game);
        Rectangle excitedLocation = new Rectangle(paratrooper.x(), newHeight, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(paratrooper.x(), paratrooper.y(), gameConfig.paratrooper().width(), gameConfig.paratrooper().height());

        assert excitedLocation.intersects(realLocation);
    }

    @Test
    public void checkMoveHelicopter() {
        Game game = new Game("movingTest", gameConfig);
        game.createHelicopter();
        HelicopterDto helicopter = lastHelicopter(game);
        while (helicopter.x() <= 597 || helicopter.x() >= 602) {
            game.updateGame();
            helicopter = lastHelicopter(game);
        }
        for (int i = 0; i < 50; i++) {
            game.updateGame();
        }
        helicopter = lastHelicopter(game);

        Rectangle excitedLocation;
        if (helicopter.direction()== Direction.RIGHT){
            excitedLocation=new Rectangle(830, 0, gameConfig.helicopter().width(), gameConfig.helicopter().height());
        }
        else{
            excitedLocation=new Rectangle(340, 0, gameConfig.helicopter().width(), gameConfig.helicopter().height());
        }
        Rectangle realLocation = new Rectangle(helicopter.x(), helicopter.y(), gameConfig.helicopter().width(), gameConfig.helicopter().height());
        assert excitedLocation.intersects(realLocation);
    }
}
