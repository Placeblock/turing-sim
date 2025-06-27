package ui.configuration;

import core.Configuration;
import event.Receiver;

import javax.swing.*;

public class BlankSymbolUI extends JPanel {

    public BlankSymbolUI(Configuration config, Receiver receiver) {
        this.add(new JLabel("Blank Symbol: "));
        BlankSymbolComboBox comboBox = new BlankSymbolComboBox(config, receiver);
        this.add(comboBox);
    }
}
