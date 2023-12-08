package org.example.view;


import javax.swing.*;

public class MenuFrame extends JFrame {

    public MenuFrame(int MenuWidth, int MenuHeight) {
        setTitle("Paratrooper Game");
        setSize(MenuWidth, MenuHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}