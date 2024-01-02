package org.example.controller;

import org.example.config.Config;
import org.example.dto.GameInfo;
import org.example.model.Game;
import org.example.model.ScoreManager;
import org.example.view.ControllerListener;
import org.example.view.NewGameListener;
import org.example.view.TableListener;
import org.example.view.View;

import javax.swing.*;

public class Controller implements Runnable, NewGameListener, ControllerListener, TableListener {
    Game game;
    View view;
    private final Config config;
    private final ScoreManager scoreManager;

    public Controller(Config config) {
        this.config = config;
        this.scoreManager = ScoreManager.getInstance(config.resultName());
    }

    public void run() {
        view = new View(this, config);
        view.runMenu(this, this);
    }

    @Override
    public void newGame() {
        view.runGame();
        String name = view.getUserName();
        game = new Game(name, config, scoreManager);
        Timer helicopterTimer = new Timer(1300, e -> game.createHelicopter());
        helicopterTimer.start();
        Timer paratrooperTimer = new Timer(1500, e -> game.createParatrooper());
        paratrooperTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            if (game.updateGame()) {
                endGame();
            }
        });
        gameTimer.start();
    }

    public void endGame() {
        scoreManager.saveScore();
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
        view.showTable();
    }
}
