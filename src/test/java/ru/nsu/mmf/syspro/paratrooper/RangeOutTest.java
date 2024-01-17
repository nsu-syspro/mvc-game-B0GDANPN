package ru.nsu.mmf.syspro.paratrooper;
import org.example.config.Config;
import org.example.model.Game;
import org.junit.Test;
public class RangeOutTest {
    @Test
    public void checkRangeOut() {
        Config gameConfig = Config.create();
        Game game = new Game("OutTest", gameConfig);
        game.createHelicopter();
        game.createHelicopter();
        game.createBullet();
        game.createBullet();
        for (int i=0;i<400;i++){
            game.updateGame();
        }
        assert game.toGameInfo().dtos().size() == 1;
    }
}
