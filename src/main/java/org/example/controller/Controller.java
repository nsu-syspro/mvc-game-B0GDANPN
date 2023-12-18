package org.example.controller;

import org.example.config.GameConfig;
import org.example.dto.GameInfo;
import org.example.dto.IndicesReduced;
import org.example.model.Game;
import org.example.view.*;

import javax.swing.*;

public class Controller implements Runnable, NewGameListener, ControllerListener, TableListener {
    Game game;
    View view;
    private final GameConfig gameConfig;

    public Controller(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void run() {
        view = new View(this, gameConfig);
        view.runMenu(this, this, this);
    }

    @Override
    public void newGame() {
        view.runGame();
        game = new Game(this, gameConfig);
        Timer helicopterTimer = new Timer(1300, e -> game.createHelicopter());
        helicopterTimer.start();
        Timer paratrooperTimer = new Timer(1500, e -> game.createParatrooper());
        paratrooperTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            game.updateGame();
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
    public IndicesReduced getIndicesReducedObjects() {
        return view.getIndicesReducedObjects();
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
