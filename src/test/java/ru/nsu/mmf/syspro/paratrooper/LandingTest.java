package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class LandingTest {
    @Test
    public void checkLanding() {
        Config gameConfig = Config.create();
        Game game = new Game("landingTest", gameConfig);
        game.createHelicopter();
        for (int i = 0; i < 10; i++) {
            game.updateGame();
        }
        boolean wasCreatedParatrooper = false;
        while (!wasCreatedParatrooper) {
            wasCreatedParatrooper = game.createParatrooper();
        }
        for (int i = 0; i < 600; i++) {
            game.updateGame();
        }
        assert game.getGroundedParatrooperCount() == 1;
    }
}
