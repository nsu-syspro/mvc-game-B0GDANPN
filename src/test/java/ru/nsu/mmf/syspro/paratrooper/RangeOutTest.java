package ru.nsu.mmf.syspro.paratrooper;
import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;
public class RangeOutTest {
    @Test
    public void checkRangeOut() {
        Config gameConfig = Config.create();
        Game game = new Game("OutTest", gameConfig);
        game.directlyCreateBullet(80000,80000,0);
        game.directlyCreateParatrooper(80000,300000);
        game.directlyCreateHelicopter(80000);
        game.updateGame();
        assert game.getCountObjects() == 1;
    }
}
