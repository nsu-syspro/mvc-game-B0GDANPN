package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.dto.*;
import org.example.model.Game;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class ChangeAngleTest {
    Config gameConfig = Config.create();

    public static BulletDto lastBullet(Game game) {
        GameInfo gameInfo = game.toGameInfo();
        BulletDto bulletDto = null;
        for (GameObjectInfo dto : gameInfo.dtos()) {
            if (dto.type() == DtoType.BULLET) {
                bulletDto = (BulletDto) dto;
            }
        }
        return bulletDto;
    }

    public static HelicopterDto lastHelicopter(Game game) {
        GameInfo gameInfo = game.toGameInfo();
        HelicopterDto helicopterDto = null;
        for (GameObjectInfo dto : gameInfo.dtos()) {
            if (dto.type() == DtoType.HELICOPTER) {
                helicopterDto = (HelicopterDto) dto;
            }
        }
        return helicopterDto;
    }

    public static ParatrooperDto lastParatrooper(Game game) {
        GameInfo gameInfo = game.toGameInfo();
        ParatrooperDto paratrooperDto = null;
        for (GameObjectInfo dto : gameInfo.dtos()) {
            if (dto.type() == DtoType.PARATROOPER) {
                paratrooperDto = (ParatrooperDto) dto;
            }
        }
        return paratrooperDto;
    }

    @Test
    public void checkRight() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(682, 720);//оружие вправо
        game.createBullet();
        BulletDto bulletDto = lastBullet(game);
        Rectangle excitedLocation = new Rectangle(682, 710, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bulletDto.x(), bulletDto.y(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assertTrue(excitedLocation.intersects(realLocation));
    }

    @Test
    public void checkUp() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(567, 597);//оружие вверх
        game.createBullet();
        BulletDto bulletDto = lastBullet(game);
        Rectangle excitedLocation = new Rectangle(567, 597, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bulletDto.x(), bulletDto.y(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assertTrue(excitedLocation.intersects(realLocation));
    }

    @Test
    public void checkLeft() throws InterruptedException {
        Game game = new Game("ChangeAngleTest", gameConfig);
        Thread.sleep(500);
        game.updateGun(454, 713);//оружие влево
        game.createBullet();
        BulletDto bulletDto = lastBullet(game);
        Rectangle excitedLocation = new Rectangle(454, 713, gameConfig.bullet().width(), gameConfig.bullet().height());
        Rectangle realLocation = new Rectangle(bulletDto.x(), bulletDto.y(), gameConfig.bullet().width(), gameConfig.bullet().height());
        assertTrue(excitedLocation.intersects(realLocation));
    }
}
