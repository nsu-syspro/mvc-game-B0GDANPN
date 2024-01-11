package org.example.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private Image backgroundImage;
    private static final String TABLE = "Records";

    private static final String NEW_GAME = "New game";
    private static final String EXIT = "Exit";

    public MenuPanel(NewGameListener newGameListener, TableListener tableListener, ExitMenuListener exitMenuListener, int width, int height) {
        this.setSize(width, height);
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/startGame.png"));
        } catch (IOException e) {
            System.out.println("Can`t open backround menu theme");
            System.exit(0);
        }
        JButton quitButton = new JButton(EXIT);
        JButton tableButton = new JButton(TABLE);
        JButton newGameButton = new JButton(NEW_GAME);
        add(quitButton);
        add(tableButton);
        add(newGameButton);
        newGameButton.setVisible(true);
        tableButton.setVisible(true);
        quitButton.setVisible(true);
        newGameButton.addActionListener(e -> newGameListener.newGame());
        tableButton.addActionListener(e -> tableListener.showTable());
        quitButton.addActionListener(e -> {
            exitMenuListener.saveScores();
            System.exit(0);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }


}