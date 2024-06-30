package org.example.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public record Config(Size menu, Size table, Size game, Size gun, Size bullet, Size soldier, Size paratrooper,
                     Size helicopter, Size barrel) {
    private static final String CONFIG_FILENAME = "src/main/resources/configuration.txt";

    public static Config create() {
        Map<String, Size> fileContent = new HashMap<>();
        String resultName = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.strip().split(" ");
                if (parts.length == 1) {
                    resultName = parts[0];
                } else {
                    String key = parts[0];
                    int width = Integer.parseInt(parts[1]);
                    int height = Integer.parseInt(parts[2]);
                    fileContent.put(key, new Size(width, height));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return defaultConfig();
        }
        Size menuSize = fileContent.get("menu");
        if (menuSize == null) return defaultConfig();
        Size gameSize = fileContent.get("game");
        if (gameSize == null) return defaultConfig();
        Size tableSize = fileContent.get("table");
        if (tableSize == null) return defaultConfig();
        Size gunSize = fileContent.get("gun");
        if (gunSize == null) return defaultConfig();
        Size bulletSize = fileContent.get("bullet");
        if (bulletSize == null) return defaultConfig();
        Size soldierSize = fileContent.get("soldier");
        if (soldierSize == null) return defaultConfig();
        Size paratrooperSize = fileContent.get("paratrooper");
        if (paratrooperSize == null) return defaultConfig();
        Size helicopterSize = fileContent.get("helicopter");
        if (helicopterSize == null) return defaultConfig();
        Size barrelSize = fileContent.get("barrel");
        if (barrelSize == null) return defaultConfig();
        if (resultName == null) return defaultConfig();
        return new Config(menuSize, tableSize, gameSize, gunSize, bulletSize, soldierSize, paratrooperSize, helicopterSize, barrelSize);
    }

    static Config defaultConfig() {
        return new Config(
                new Size(900, 540),
                new Size(600, 600),
                new Size(1200, 800),
                new Size(77, 114),
                new Size(32, 32),
                new Size(72, 72),
                new Size(72, 90),
                new Size(200, 95),
                new Size(77, 114));
    }
}