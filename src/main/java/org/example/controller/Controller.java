package org.example.controller;

import org.example.model.*;
import org.example.view.*;
import org.example.dto.*;

import javax.swing.*;

public class Controller implements Runnable, NewGameListener, ControllerListener, TableListener, ExitMenuListener {
    Game game;
    View view;

    public void run() {
        view = new View(this);
        view.runMenu(this, this, this);
    }

    @Override
    public void newGame() {
        view.runGame();
        game = new Game(this, View.getGameWidth(), View.getGameHeight(), View.getGunWidth(), View.getGunHeight());

        Timer helicopterTimer = new Timer(1300, e -> game.createHelicopter(View.getGameWidth()));
        helicopterTimer.start();
        Timer parachutistTimer = new Timer(1500, e -> game.createParachutist(View.getParachutistWidth(), View.getParachutistHeight()));
        parachutistTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            game.updateGame(View.getGameHeight(), View.getSoldierHeight());
        });
        gameTimer.start();
    }

    public void endGame() {
        view.endGame();
    }

    @Override
    public void createBullet() {
        game.createBullet(View.getGunWidth(), View.getGunHeight(), View.getBulletWidth(), View.getBulletHeight());
    }

    @Override
    public void updateGun(int mouseX, int mouseY) {
        game.updateGun(mouseX, mouseY, View.getGunWidth(), View.getGunHeight());
    }

    @Override
    public IndicesReduced getIndicesReducedObjects() {
        return view.getIndicesReducedObjects();
    }

    @Override
    public void exitMenu() {
        view.exitMenu();
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
