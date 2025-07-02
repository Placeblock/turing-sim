package ui;

import controller.ConfigurationController;
import core.Configuration;
import core.StateRegister;
import ui.configuration.BlankSymbolUI;
import ui.configuration.InitialStateUI;
import ui.configuration.TapeSymbolsUI;
import util.SampleStateRegister;

import javax.swing.*;
import java.util.ArrayList;

public class ConfigurationWindow extends JFrame {
    public ConfigurationWindow(Configuration config) {
        setTitle("Turing Configuration");
        setSize(800, 450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        // vertical layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        ConfigurationController controller = new ConfigurationController(config, new StateRegister(new ArrayList<>()));

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
            StateRegister stateRegister = SampleStateRegister.get();
            Configuration config = new Configuration(stateRegister.getStates().getFirst());
            config.setInitialTapeState("000002020202");

            ConfigurationWindow configurationWindow = new ConfigurationWindow(config);
        });
    }
}
