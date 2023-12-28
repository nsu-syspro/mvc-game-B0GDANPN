package org.example;
import org.example.controller.Controller;
import org.example.config.Config;

public class Main {
    public static void main(String[] args) {
        Config gameConfig = Config.create();
        Controller controller = new Controller(gameConfig);
        controller.run();
    }
}