package ui;

import controller.ConfigurationController;
import core.Configuration;
import ui.configuration.BlankSymbolUI;
import ui.configuration.InitialStateUI;
import ui.configuration.TapeSymbolsUI;

import javax.swing.*;
import java.awt.*;

public class ConfigurationWindow extends JFrame {
    public ConfigurationWindow() {
        setTitle("Turing Configuration");
        setSize(800, 450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        // vertical layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Configuration config = new Configuration();
        ConfigurationController controller = new ConfigurationController(config);

        InitialStateUI initialStateUI = new InitialStateUI(config, controller.getReceiver());
        add(initialStateUI);
        BlankSymbolUI blankSymbolUI = new BlankSymbolUI(config, controller.getReceiver());
        add(blankSymbolUI);
        TapeSymbolsUI tapeSymbolsUI = new TapeSymbolsUI(config, controller.getReceiver());
        add(tapeSymbolsUI);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ConfigurationWindow configurationWindow = new ConfigurationWindow();
        });
    }
}
