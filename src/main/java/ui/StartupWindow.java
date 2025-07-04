package ui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class StartupWindow extends JFrame {

    private JLabel tapeLabel;
    private JLabel transitionsLabel;
    private JButton startButton;

    private boolean isTapeLoaded = false;
    private boolean areTransitionsLoaded = false;

    public StartupWindow() {
        setTitle("Turing Machine Simulator");
        setSize(400, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Create main panel with vertical layout and center alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Load Tape button and label
        JPanel tapePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadTapeButton = new JButton("Load Tape");
        loadTapeButton.addActionListener(this::loadTapeActionListener);
        tapeLabel = new JLabel("Tape needs to be loaded");
        tapeLabel.setForeground(Color.RED);
        tapePanel.add(loadTapeButton);
        tapePanel.add(tapeLabel);
        
        // Load Transitions button and label
        JPanel transitionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadTransitionsButton = new JButton("Load Transitions");
        loadTransitionsButton.addActionListener(this::loadTransitionsActionListener);
        transitionsLabel = new JLabel("Transitions need to be loaded");
        transitionsLabel.setForeground(Color.RED);
        transitionsPanel.add(loadTransitionsButton);
        transitionsPanel.add(transitionsLabel);
        
        // Start button (disabled)
        JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = new JButton("Start!");
        startButton.setEnabled(false);
        startButton.addActionListener(this::startButtonActionListener);
        startPanel.add(startButton);

        // Start with example data button
        JButton startWithExampleButton = new JButton("Start with example data");
        startWithExampleButton.addActionListener(this::startWithExampleActionListener);
        startPanel.add(startWithExampleButton);
        
        // Add vertical glue to center content vertically
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(tapePanel);
        mainPanel.add(transitionsPanel);
        mainPanel.add(startPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel);
        setVisible(true);
    }

    private void loadTapeActionListener(ActionEvent e) {
        // Open file chooser, for now just println the selected file path
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Tape File");

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // TODO: Implement tape loading logic
            System.out.println("Selected tape file: " + selectedFile.getAbsolutePath()); //!TEST

            // When tape is loaded successfully
            isTapeLoaded = true;
            tapeLabel.setText("Tape loaded");
            tapeLabel.setForeground(Color.GREEN);

            // Enable start button if both tape and transitions are loaded
            maybeEnableStartButton();
        }
    }

    private void loadTransitionsActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Transitions File");

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // TODO: Implement transitions loading logic
            System.out.println("Selected transitions file: " + selectedFile.getAbsolutePath()); //!TEST

            // When transitions are loaded successfully
            areTransitionsLoaded = true;
            transitionsLabel.setText("Transitions loaded");
            transitionsLabel.setForeground(Color.GREEN);

            // Enable start button if both tape and transitions are loaded
            maybeEnableStartButton();
        }
    }

    private void maybeEnableStartButton() {
        if (isTapeLoaded && areTransitionsLoaded) {
            startButton.setEnabled(true);
        } else {
            startButton.setEnabled(false);
        }
    }

    private void startButtonActionListener(ActionEvent e) {
        // TODO: Implement start button logic
        System.out.println("Start button clicked");

        //setVisible(false);
    }

    private void startWithExampleActionListener(ActionEvent e) {
        // TODO: Implement logic to start with example data
        System.out.println("Start with example data button clicked");

        //setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartupWindow startupWindow = new StartupWindow();
        });
    }
}
