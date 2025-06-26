package ui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ui.tape.TapeUI;

import java.awt.FlowLayout;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Turing Maschine");
        setSize(800, 450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadCsvItem = new JMenuItem("Load CSV");
        loadCsvItem.addActionListener(e -> openFileChooser());
        fileMenu.add(loadCsvItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JButton openButton = new JButton("Open File");
        openButton.addActionListener(e -> openFileChooser());

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(openButton);

        TapeUI tapePanel = new TapeUI(5, 5);
        getContentPane().add(tapePanel);

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
