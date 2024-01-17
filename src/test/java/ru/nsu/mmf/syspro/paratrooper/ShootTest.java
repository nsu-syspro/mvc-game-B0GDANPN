package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;

public class ShootTest {
    Config gameConfig = Config.create();

    @Test
    public void checkShoot() throws InterruptedException {
        Game game = new Game("ShootTest", gameConfig);
        Thread.sleep(1000);// чтобы произошла задержка и пуля могла создаться
        game.createBullet();
        assert game.toGameInfo().dtos().size()== 2;
    }
}
