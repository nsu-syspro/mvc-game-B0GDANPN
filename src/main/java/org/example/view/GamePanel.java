package org.example.view;


import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;

import org.example.dto.GameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<GameObject> gameObjects;
    private ControllerListener controllerListener;
    private Image backgroundImage;

    public GamePanel(ControllerListener controllerListener, int width, int height, ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.controllerListener = controllerListener;
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/gamebackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controllerListener.createBullet();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controllerListener.updateGunPosition(e.getX(), e.getY());
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        for (GameObject object : gameObjects) {
            object.draw(g);
        }
    }

}
