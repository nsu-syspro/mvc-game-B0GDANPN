package org.example.view;

import org.example.utils.Score;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

public class TableFrame extends JFrame {
    public TableFrame(int WIDTH, int HEIGHT, List<Score> scores) {
        setTitle("Table of results");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        scores.sort(Comparator.comparingInt(Score::score).reversed());
        for (Score score : scores) {
            textArea.append(score.name() + " " + score.score() + "\n");
        }

        // Create a JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the JScrollPane to the JFrame
        getContentPane().add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}

