package ui;

import core.Configuration;
import event.Receiver;
import ui.configuration.TapeSymbolsUI;

import javax.swing.*;

public class ConfigurationWindow extends JFrame {
    public ConfigurationWindow() {
        setTitle("Turing Configuration");
        setSize(800, 450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        TapeSymbolsUI tapeSymbolsUI = new TapeSymbolsUI(new Configuration(), new Receiver());
        getContentPane().add(tapeSymbolsUI);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ConfigurationWindow configurationWindow = new ConfigurationWindow();
        });
    }
}
