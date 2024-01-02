package org.example.model;

import org.example.dto.Score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// CR: add scores with names, add score manager

public class ScoreManager {
    private final String fileName;
    private int currentScore;
    private String currentName;
    private static ScoreManager instance = null;

    private ScoreManager(String resultName) {
        fileName = resultName;
    }

    public static ScoreManager getInstance(String resultName) {
        if (instance == null) {
            instance = new ScoreManager(resultName);
        }
        return instance;
    }

    public void clearCurrent() {
        currentScore = 0;
        currentName = null;
    }

    public void setCurrentName(String name) {
        currentName = name;
    }

    public void addScore(int difference) {
        currentScore += difference;
    }

    public void saveScore() {
        if (currentName == null) {
            return;
        }
        try (FileWriter file = new FileWriter(fileName, true)) {
            file.write(currentName + " " + currentScore + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Score> getScoresAndNames() {
        ArrayList<Score> scores = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                scores.add(new Score(name, score));
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;

    }
}