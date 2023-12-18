package org.example.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private final NewGameListener newGameListener;
    private final TableListener tableListener;
    private final ExitMenuListener exitMenuListener;
    private Image backgroundImage;
    private static final String TABLE = "Records";

    private static final String NEW_GAME = "New game";
    private static final String EXIT = "Exit";

    public MenuPanel(NewGameListener newGameListener, TableListener tableListener, ExitMenuListener exitMenuListener, int width, int height) {
        this.exitMenuListener = exitMenuListener;
        this.newGameListener = newGameListener;
        this.tableListener = tableListener;
        this.setSize(width, height);
        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/startGame.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        JButton quitButton = new JButton(EXIT);
        JButton tableButton= new JButton(TABLE);
        JButton newGameButton = new JButton(NEW_GAME);
        add(quitButton);
        add(tableButton);
        add(newGameButton);
        newGameButton.setVisible(true);
        tableButton.setVisible(true);
        quitButton.setVisible(true);
        newGameButton.addActionListener(e -> newGameListener.newGame());
        tableButton.addActionListener(e -> tableListener.showTable());
        quitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }


}