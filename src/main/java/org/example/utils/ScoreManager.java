package org.example.utils;

import java.io.FileWriter;
import java.io.IOException;

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
            System.out.println("Error: " + e.getMessage());
        }
    }
}