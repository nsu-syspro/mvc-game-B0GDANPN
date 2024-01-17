package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class ScoreTest {
    Config gameConfig = Config.create();
    @Test
    public void checkScore() {
        Game game = new Game("HitTest", gameConfig);
        game.directlyCreateBullet(620,420,0);
        game.directlyCreateParatrooper(600,400);
        game.updateGame();
        game.directlyCreateBullet(620,10,0);
        game.directlyCreateHelicopter(600);
        game.updateGame();
        assert game.getScore() == 2;
    }
}
