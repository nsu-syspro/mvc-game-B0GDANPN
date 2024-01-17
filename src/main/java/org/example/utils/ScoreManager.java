package org.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ScoreManager {
    private final List<Score> scores;
    private final static int MAX_SCORES = 5;
    private static final String SCORES_FILE = "result.txt";
    private static ScoreManager INSTANCE = null;


    public ScoreManager() {
        this.scores = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(SCORES_FILE))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                scores.add(new Score(line[0], Integer.parseInt(line[1])));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ScoreManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScoreManager();
        }
        return INSTANCE;
    }

    public boolean addScore(String name, int score) {//зачем boolean?
        scores.sort(Comparator.comparingInt(Score::score).reversed());
        int i = 0;
        for (; i < scores.size(); i++) {
            Score el = scores.get(i);
            if (score > el.score()) {
                break;
            }
        }
        boolean wasAdded = i < MAX_SCORES;
        scores.add(new Score(name, score));
        scores.sort(Comparator.comparingInt(Score::score).reversed());
        if (scores.size() > MAX_SCORES) {
            scores.removeLast();
        }
        return wasAdded;
    }

    public void saveScores() {
        if (INSTANCE == null) {
            return;
        }
        try (FileWriter file = new FileWriter(SCORES_FILE, false)) {
            for (Score score : scores) {
                file.write(score.name() + " " + score.score() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Can`t open file with scores");
            System.exit(0);
        }
    }

    public List<Score> getScores() {
        return scores;
    }
}