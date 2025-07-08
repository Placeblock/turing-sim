package ui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import core.Configuration;
import core.StateRegister;
import serialization.ConfigSerializer;
import util.SampleStateRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

public class StartupWindow extends JFrame {

    private JLabel tapeLabel;
    private JButton loadTapeButton;
    private JLabel transitionsLabel;
    private JButton startButton;

    private boolean isTapeLoaded = false;
    private boolean areTransitionsLoaded = false;

    private static final Color colorDarkGreen = new Color(0x008800);

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
        
        // Load Transitions button and label
        JPanel transitionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadTransitionsButton = new JButton("Load Config");
        loadTransitionsButton.addActionListener(this::loadTransitionsActionListener);
        transitionsLabel = new JLabel("Config needs to be loaded");
        transitionsLabel.setForeground(Color.RED);
        transitionsPanel.add(loadTransitionsButton);
        transitionsPanel.add(transitionsLabel);
        
        // Load Tape button and label
        JPanel tapePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loadTapeButton = new JButton("Load Tape");
        loadTapeButton.addActionListener(this::loadTapeActionListener);
        loadTapeButton.setEnabled(false);
        tapeLabel = new JLabel("Tape needs to be loaded");
        tapeLabel.setForeground(Color.RED);
        tapePanel.add(loadTapeButton);
        tapePanel.add(tapeLabel);
        
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
        mainPanel.add(transitionsPanel);
        mainPanel.add(tapePanel);
        mainPanel.add(startPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel);
    }

    private Configuration config;
    private StateRegister stateRegister;

    private void loadTransitionsActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Config File");

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            boolean success = false;
            try {
                var halloHerrKaupp = ConfigSerializer.deserialize(new FileInputStream(selectedFile));
                config = halloHerrKaupp.getConfig();
                stateRegister = halloHerrKaupp.getRegister();
                success = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(!success) {
                transitionsLabel.setText("Failed to load config");
                transitionsLabel.setForeground(Color.RED);
                return;
            }

            // When config is loaded successfully
            areTransitionsLoaded = true;
            transitionsLabel.setText("Config loaded");
            transitionsLabel.setForeground(colorDarkGreen);
            loadTapeButton.setEnabled(true);

            // Enable start button if both tape and config are loaded
            maybeEnableStartButton();
        }
    }

    private void loadTapeActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Tape File");

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            boolean success = false;
            try (var reader = new BufferedReader(new FileReader(selectedFile))) {
                config.setInitialTapeState(reader.readLine().trim());
                success = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (!success) {
                tapeLabel.setText("Failed to load tape");
                tapeLabel.setForeground(Color.RED);
                return;
            }

            // When tape is loaded successfully
            isTapeLoaded = true;
            tapeLabel.setText("Tape loaded");
            tapeLabel.setForeground(colorDarkGreen);

            // Enable start button if both tape and config are loaded
            maybeEnableStartButton();
        }
    }

    private void maybeEnableStartButton() {
        if (isTapeLoaded && areTransitionsLoaded && config != null && stateRegister != null) {
            startButton.setEnabled(true);
        } else {
            startButton.setEnabled(false);
        }
    }

    private void startButtonActionListener(ActionEvent e) {
        MainWindow mainWindow = new MainWindow(config, stateRegister);
        mainWindow.setVisible(true);

        setVisible(false);
    }

    private void startWithExampleActionListener(ActionEvent e) {
        StateRegister stateRegister = SampleStateRegister.get();
        Configuration config = new Configuration(stateRegister.getStates().getFirst());

        config.setInitialTapeState("000001010101");

        MainWindow mainWindow = new MainWindow(config, stateRegister);
        mainWindow.setVisible(true);

        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartupWindow startupWindow = new StartupWindow();
            startupWindow.setVisible(true);
        });
    }
}
