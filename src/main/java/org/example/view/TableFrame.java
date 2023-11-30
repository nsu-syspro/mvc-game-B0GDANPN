package org.example.view;

import javax.swing.*;
import java.io.StringReader;
import java.util.ArrayList;

public class TableFrame extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    public TableFrame(StringBuilder content) {
        setTitle("Table of results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] lines = content.toString().split("\n");
        DefaultListModel<String> l = new DefaultListModel<>();
        for (String record: lines) {
            l.addElement(record);
        }
        JList<String> list = new JList<>(l);
        list.setBounds(100,100, 75,75);
        setSize(WIDTH,HEIGHT);
        add(list);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }
}

