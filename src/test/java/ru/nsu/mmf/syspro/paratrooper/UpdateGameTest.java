package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateGameTest {
    Config gameConfig = Config.create();

    @Test
    public void checkUpdate() {
        Game game = new Game("UpdateTest", gameConfig);
        game.createBullet();
        game.createHelicopter();
        boolean wasCreated = false;
        while (!wasCreated) {
            wasCreated = game.createParatrooper();
        }
        for (int i = 0; i < 400; i++) {
            game.updateGame();
        }
        assertEquals(game.toGameInfo().dtos().size(), 2);
    }
}
