package org.example.controller;

import org.example.config.Config;
import org.example.dto.GameInfo;
import org.example.model.Game;
import org.example.utils.ScoreManager;
import org.example.view.*;

import javax.swing.*;

public class Controller implements Runnable, NewGameListener, ControllerListener, TableListener, ExitMenuListener {
    private Game game;
    private View view;
    private final Config config;
    private final ScoreManager scoreManager;
    private Timer helicopterTimer;
    private Timer paratrooperTimer;
    private Timer gameTimer;

    public Controller(Config config) {
        this.config = config;
        this.scoreManager = ScoreManager.getInstance();
    }

    public void run() {
        view = new View(this, config);
        view.runMenu(this, this, this);
    }

    @Override
    public void newGame() {
        view.runGame();
        String name = view.getUserName();
        game = new Game(name, config);
        helicopterTimer = new Timer(1400, e -> game.createHelicopter());
        helicopterTimer.start();
        paratrooperTimer = new Timer(1500, e -> game.createParatrooper());
        paratrooperTimer.start();
        gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            if (game.updateGame()) {
                stopTimers();
                endGame();

            }
        });
        gameTimer.start();
    }

    private void stopTimers() {
        helicopterTimer.stop();
        paratrooperTimer.stop();
        gameTimer.stop();
    }

    @Override
    public void saveScores() {
        scoreManager.saveScores();
    }

    public void endGame() {
        int score = game.getScore();
        String name = game.getName();
        ScoreManager.getInstance().addScore(name, score);
        view.endGame(name, score);
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
        view.showTable(ScoreManager.getInstance().getScores());
    }
}
