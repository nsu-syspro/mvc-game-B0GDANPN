package org.example;
import org.example.controller.Controller;
import org.example.config.GameConfig;

public class Main {
    public static void main(String[] args) {
        GameConfig gameConfig = GameConfig.fromFile();
        Controller controller = new Controller(gameConfig);
        controller.run();
    }
}