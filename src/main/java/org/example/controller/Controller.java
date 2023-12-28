package org.example.controller;

import org.example.config.Config;
import org.example.dto.GameInfo;
import org.example.model.Game;
import org.example.view.*;

import javax.swing.*;

public class Controller implements Runnable, NewGameListener, ControllerListener, TableListener {
    Game game;
    View view;
    private final Config config;

    public Controller(Config config) {
        this.config = config;
    }

    public void run() {
        view = new View(this, config);
        view.runMenu(this, this);
    }

    @Override
    public void newGame() {
        view.runGame();
        game = new Game(config);
        Timer helicopterTimer = new Timer(1300, e -> game.createHelicopter());
        helicopterTimer.start();
        Timer paratrooperTimer = new Timer(1500, e -> game.createParatrooper());
        paratrooperTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            if (game.updateGame()){
                endGame();
            }
        });
        gameTimer.start();
    }

    public void endGame() {
        view.endGame();
    }

    @Override
    public void createBullet() {
        game.createBullet();
    }

    @Override
    public void updateGun(int mouseX, int mouseY) {
        game.updateGun(mouseX, mouseY);
    }
    @Override
    public void showTable() {
        if (game == null) {
            view.showTable(0);
        } else {
            view.showTable(game.getScore());
        }
    }
}
