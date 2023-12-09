package org.example.controller;

import org.example.model.*;
import org.example.view.*;
import org.example.dto.*;

import javax.swing.*;
public  class Controller implements Runnable, NewGameListener, ControllerListener,TableListener,ExitMenuListener {
    Game game;
    View view;
    public void run() {
        view = new View(this,this,this,this);
        view.runMenu(this,this,this);
    }
    @Override
    public void newGame() {
        view.runGame();
        game = new Game(this, View.getGameWidth(), View.getGameHeight(), View.getGunWidth(), View.getGunHeight());

        Timer helicopterTimer = new Timer(1000, e -> game.createHelicopter(View.getGameWidth()));
        helicopterTimer.start();
        Timer parachutistTimer = new Timer(1100, e -> game.createParachutist(View.getParachutistWidth(), View.getParachutistHeight()));
        parachutistTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            game.updateGame(View.getGameHeight(), View.getSoldierHeight());
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
            if (game.getnParachutistsReachedGround()>=5){
                endGame();
            }
        });
        gameTimer.start();
    }
    public void endGame(){
        int score=game.getScore();

        view.endGame(score);
    }
    @Override
    public void createBullet(){
        game.createBullet(View.getGunWidth(), View.getGunHeight(), View.getBulletWidth(), View.getBulletHeight());
    }
    @Override
    public void updateGun(int mouseX, int mouseY){
        game.updateGun(mouseX, mouseY, View.getGunWidth(), View.getGunHeight());
    }

    @Override
    public IndicesReduced getIndicesReducedObjects(){
        return view.getIndicesReducedObjects();
    }

    @Override
    public void exitMenu() {
        view.exitMenu();
    }

    @Override
    public void showTable() {
        view.showTable();
    }
}
