package ui.stateregister;

import core.StateRegister;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class TransitionPanel extends JPanel {
    private final StateRegister stateRegister;
    private Set tapeAlphabet;

    public TransitionPanel (StateRegister stateRegister, int index){
        this.stateRegister = stateRegister;

        JPanel panel = new JPanel(new FlowLayout((FlowLayout.LEFT)));
        JComboBox stateComboBox = new JComboBox<>();
        for (int i = 0; i < stateRegister.getStates().size(); i++) {
            stateComboBox.addItem(stateRegister.getStates().get(i));
        }

        JComboBox symbolComboBox = new JComboBox();

        JComboBox moveComboBox = new JComboBox();


    
    }
}
