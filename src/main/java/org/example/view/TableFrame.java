package org.example.view;

import javax.swing.*;

public class TableFrame extends JFrame {

    public TableFrame(StringBuilder content) {
        setTitle("Table of results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] lines = content.toString().split("\n");
        DefaultListModel<String> l = new DefaultListModel<>();
        for (String record : lines) {
            l.addElement(record);
        }
        JList<String> list = new JList<>(l);
        list.setBounds(100, 100, 75, 75);
        setSize(WIDTH, HEIGHT);
        add(list);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
    }
}

