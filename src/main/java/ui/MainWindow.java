package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.tape.Tape;
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


        List<Character> symbols = new ArrayList<>();
        symbols.add('0');
        symbols.add('0');
        symbols.add('0');
        symbols.add('0');
        symbols.add('0');
        symbols.add('2');
        Tape<Character> tape = new Tape<>('B', symbols);

        TapeUI tapePanel = new TapeUI(tape);
        getContentPane().add(tapePanel);


        JButton testCharacterChangedEventButton = new JButton("Test Character Changed Event");
        testCharacterChangedEventButton.addActionListener(e -> {
            tapePanel.aaa.setSymbol('T');
        });
        getContentPane().add(testCharacterChangedEventButton);

        JButton testHeadChangedEventButton = new JButton("Test Head Changed Event");
        testHeadChangedEventButton.addActionListener(e -> {
            tape.moveNext();
        });
        getContentPane().add(testHeadChangedEventButton);

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
