package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.dto.HelicopterDto;
import org.example.model.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.nsu.mmf.syspro.paratrooper.ChangeAngleTest.lastHelicopter;

public class GetNameAndScoreTest {
    Config gameConfig = Config.create();

    @Test
    public void checkScore() throws InterruptedException {
        Game game = new Game("nameTest", gameConfig);
        game.createHelicopter();
        HelicopterDto helicopter = lastHelicopter(game);
        while (helicopter.x() <= 597 || helicopter.x() >= 604) {
            game.updateGame();
            helicopter = lastHelicopter(game);
        }
        boolean wasCreated = game.createParatrooper();
        while (!wasCreated) {
            wasCreated = game.createParatrooper();
        }
        game.updateGun(615, 615);//вверрх стрельба
        Thread.sleep(500);
        game.createBullet();
        while (game.toGameInfo().dtos().size() > 1) {
            game.updateGame();
        }
        assertEquals(game.getScore(),1);
        assertEquals(game.getName(),"nameTest");
    }
}
