package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class HitTest {
    @Test
    public void checkHit() {
        Config gameConfig = Config.create();
        Game game = new Game("landingTest", gameConfig);
        game.directlyCreateBullet(620,420);
        game.directlyCreateParatrooper(600,400);
        game.updateGame();
        assert game.getCountObjects() == 1;
    }
}
