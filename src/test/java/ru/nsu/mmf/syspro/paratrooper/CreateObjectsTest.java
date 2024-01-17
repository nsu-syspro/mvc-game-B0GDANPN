package ru.nsu.mmf.syspro.paratrooper;
import org.example.config.Config;
import org.example.model.*;
import org.junit.Test;

import java.awt.*;

import static java.lang.Math.PI;

public class CreateObjectsTest {
    Config gameConfig = Config.create();

    @Test
    public void checkCreateBullet() throws InterruptedException {
        Game game = new Game("CreateTest", gameConfig);
        Thread.sleep(500);
        game.createBullet();
        Thread.sleep(500);
        game.createBullet();
        Thread.sleep(500);
        game.createBullet();
        game.updateGame();
        assert game.getCountObjects()==4;
    }
    @Test
    public void checkCreateHelicopter() {
        Game game = new Game("CreateTest", gameConfig);
        game.createHelicopter();
        game.createHelicopter();
        game.updateGame();
        assert game.getCountObjects()==3;
    }
    @Test
    public void checkCreateParatrooper() {
        Game game = new Game("CreateTest", gameConfig);
        game.createHelicopter();
        boolean wasCreated=false;
        while (!wasCreated){
            wasCreated=game.createParatrooper();
        }
        game.updateGame();
        assert game.getCountObjects()==3;
    }
}
