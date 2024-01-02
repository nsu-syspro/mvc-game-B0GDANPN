package org.example.view;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TableFrame extends JFrame {
    public TableFrame(int WIDTH, int HEIGHT, String fileName) {
        setTitle("Table of results");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textArea.append(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the JScrollPane to the JFrame
        getContentPane().add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
}

