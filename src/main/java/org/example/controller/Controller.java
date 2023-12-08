package org.example.controller;

import org.example.model.*;
import org.example.view.*;
import org.example.dto.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

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
        game = new Game(this,view.getGameWidth(), view.getGameHeight(), view.getGunWidth(), view.getGunHeight());

        Timer helicopterTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.createHelicopter(view.getGameWidth());
            }
        });
        helicopterTimer.start();
        Timer parachutistTimer = new Timer(1100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.createParachutist(view.getParachutistWidth(), view.getParachutistHeight());
            }
        });
        parachutistTimer.start();
        Timer gameTimer = new Timer(100, e -> {
            GameInfo gameInfo = game.toGameInfo();
            view.setGameInfo(gameInfo);
        });
        gameTimer.start();
    }

    @Override
    public void createBullet(){
        game.createBullet(view.getGunWidth(), view.getGunHeight(), view.getBulletWidth(), view.getBulletHeight());
    }
    @Override
    public void updateGun(int mouseX, int mouseY){
        game.updateGun(mouseX, mouseY, view.getGunWidth(), view.getGunHeight());
    }

    @Override
    public List<Objects> getIntersectedBullets() {
        view.getIntersectedBullets
    }

    @Override
    public List<Objects> getIntersectedParachutists() {
        return null;
    }

    @Override
    public List<Objects> getIntersectedHelicopters() {
        return null;
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
