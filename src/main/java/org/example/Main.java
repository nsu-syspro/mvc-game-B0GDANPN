package org.example;

import org.example.config.Config;
import org.example.controller.Controller;

public class Main {
    public static void main(String[] args) {
        Config gameConfig = Config.create();
        Controller controller = new Controller(gameConfig);
        controller.run();
    }
}