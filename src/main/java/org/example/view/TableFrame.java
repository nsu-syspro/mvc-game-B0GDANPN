package org.example.view;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TableFrame extends JFrame {
    public TableFrame(int WIDTH, int HEIGHT, String fileName) {
        setTitle("Table of results");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        File file=new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Can`t create file of results");
            System.exit(0);
        }
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textArea.append(line+'\n');
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Can`t open file of results");
            System.exit(0);
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

