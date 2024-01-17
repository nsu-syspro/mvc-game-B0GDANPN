package ru.nsu.mmf.syspro.paratrooper;
import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;
public class UpdateGameTest {
    Config gameConfig = Config.create();

    @Test
    public void checkUpdate() {
        Game game = new Game("UpdateTest", gameConfig);
        game.directlyCreateParatrooper(50, 700);
        game.directlyCreateBullet(50, 700,0);
        game.directlyCreateHelicopter(999999);
            game.updateGame();
        assert game.getCountObjects() == 1;
    }
}
