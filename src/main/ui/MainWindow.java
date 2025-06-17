package main.ui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Turing Maschine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvItem = new JMenuItem("Load CSV");
        loadCsvItem.addActionListener(_ -> openFileChooser());
        fileMenu.add(loadCsvItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JButton openButton = new JButton("Open File");
        openButton.addActionListener(_ -> openFileChooser());

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(openButton);

        setVisible(true);
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected file: " + selectedFile.getAbsolutePath());
        }
    }
}
