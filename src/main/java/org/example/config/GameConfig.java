package org.example.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GameConfig {
    private static class Pair {
        public int width;
        public int height;

        public Pair(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private static final String CONFIG_FILENAME = "src/main/resources/configuration.txt";
    private final HashMap<String, Pair> dictionary = new HashMap<>();

    public static GameConfig fromFile() {
        GameConfig gameConfig = new GameConfig();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILENAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.strip().split(" ");
                String key = parts[0];
                int width = Integer.parseInt(parts[1]);
                int height = Integer.parseInt(parts[2]);
                gameConfig.dictionary.put(key, new Pair(width, height));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameConfig;
    }
    public int getMenuWidth() {
        return dictionary.get("menu").width;
    }

    public int getMenuHeight() {
        return dictionary.get("menu").height;
    }
    public int getGameWidth() {
        return dictionary.get("game").width;
    }

    public int getGameHeight() {
        return dictionary.get("game").height;
    }

    public int getTableWidth() {
        return dictionary.get("table").width;
    }

    public int getTableHeight() {
        return dictionary.get("table").height;
    }

    public int getGunWidth() {
        return dictionary.get("gun").width;
    }

    public int getGunHeight() {
        return dictionary.get("gun").height;
    }

    public int getBulletWidth() {
        return dictionary.get("bullet").width;
    }

    public int getBulletHeight() {
        return dictionary.get("bullet").height;
    }

    public int getSoldierWidth() {
        return dictionary.get("soldier").width;
    }

    public int getSoldierHeight() {
        return dictionary.get("soldier").height;
    }

    public int getParatrooperWidth() {
        return dictionary.get("paratrooper").width;
    }

    public int getParatrooperHeight() {
        return dictionary.get("paratrooper").height;
    }

    public int getHelicopterWidth() {
        return dictionary.get("helicopter").width;
    }

    public int getHelicopterHeight() {
        return dictionary.get("helicopter").height;
    }

    public int getBarrelWidth() {
        return dictionary.get("barrel").width;
    }

    public int getBarrelHeight() {
        return dictionary.get("barrel").height;
    }
}