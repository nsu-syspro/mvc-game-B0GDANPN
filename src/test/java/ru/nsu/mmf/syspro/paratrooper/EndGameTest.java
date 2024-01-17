package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class EndGameTest {
    Config gameConfig = Config.create();

    @Test
    public void checkEnd() {
        Game game = new Game("EndTest", gameConfig);
        game.createHelicopter();
        while (game.toGameInfo().dtos().size() < 8) {
            game.createParatrooper();
        }
        for (int i = 0; i < 400; i++) {
            game.updateGame();
        }
        assert game.updateGame();
    }
}
