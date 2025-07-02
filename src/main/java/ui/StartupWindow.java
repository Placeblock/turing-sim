package ui;

import javax.swing.*;
import java.awt.*;

public class StartupWindow extends JFrame {
    public StartupWindow() {
        setTitle("Turing Machine Simulator");
        setSize(300, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        
        // Create main panel with vertical layout and center alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Load Tape button and label
        JPanel tapePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadTapeButton = new JButton("Load Tape");
        JLabel tapeLabel = new JLabel("TEST");
        tapePanel.add(loadTapeButton);
        tapePanel.add(tapeLabel);
        
        // Load Transitions button and label
        JPanel transitionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadTransitionsButton = new JButton("Load Transitions");
        JLabel transitionsLabel = new JLabel("TEST");
        transitionsPanel.add(loadTransitionsButton);
        transitionsPanel.add(transitionsLabel);
        
        // Start button (disabled)
        JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Start!");
        startButton.setEnabled(false);
        startPanel.add(startButton);
        
        // Add vertical glue to center content vertically
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(tapePanel);
        mainPanel.add(transitionsPanel);
        mainPanel.add(startPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel);
    }

    public void showWindow() {
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartupWindow startupWindow = new StartupWindow();
            startupWindow.showWindow();
        });
    }
}
