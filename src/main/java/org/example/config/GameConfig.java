package org.example.config;

import java.nio.file.Files;

public record GameConfig(int gameWidth, int gameHeight) {

    private static final String CONFIG_FILENAME = "config.txt";

    public static GameConfig fromFile() {
        // TODO: read fom config.txt
        return new GameConfig();
    }
}
