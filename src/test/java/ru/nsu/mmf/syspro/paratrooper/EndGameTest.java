package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class EndGameTest {
    Config gameConfig = Config.create();

    @Test
    public void checkEnd() {
        Game game = new Game("HitTest", gameConfig);
        game.directlyCreateParatrooper(50, 700);
        game.directlyCreateParatrooper(150, 700);
        game.directlyCreateParatrooper(200, 700);
        game.directlyCreateParatrooper(400, 700);
        game.directlyCreateParatrooper(600, 700);
        for (int i = 0; i < 50; i++) {
            game.updateGame();
        }
        assert game.wasEnd();
    }
}
