package ru.nsu.mmf.syspro.paratrooper;

import org.example.config.Config;
import org.example.model.Game;
import org.example.utils.Score;
import org.example.utils.ScoreManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreManagerTest {
    Config gameConfig = Config.create();

    @Test
    public void checkScoreManager() throws InterruptedException {
        Game game = new Game("CheckScoreManagerTest", gameConfig);
        game.directlyCreateBullet(620, 420, 0);
        game.directlyCreateParatrooper(600, 400);
        game.updateGame();
        int score = game.getScore();
        String name = game.getName();
        ScoreManager.getInstance().addScore(name, score);
        ScoreManager.getInstance().saveScores();
        ArrayList<Score> scores = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("result.txt"))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                scores.add(new Score(line[0], Integer.parseInt(line[1])));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        assert scores.size() == 1;
        assert scores.getFirst().name().equals("CheckScoreManagerTest");
        assert scores.getFirst().score() == 1;
    }
}
