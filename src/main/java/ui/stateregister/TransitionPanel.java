package ui.stateregister;

import core.Move;
import core.State;
import core.StateRegister;
import core.Transition;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class TransitionPanel extends JPanel {
    private final StateRegister stateRegister;
    private Set<Character> tapeAlphabet;

    public TransitionPanel (StateRegister stateRegister, Transition transition){
        this.stateRegister = stateRegister;

        JPanel panel = new JPanel(new FlowLayout((FlowLayout.LEFT)));
        JComboBox stateComboBox = new JComboBox<>();
        for (int i = 0; i < stateRegister.getStates().size(); i++) {
            stateComboBox.addItem(i);
            // stateComboBox.addItem(stateRegister.getStates().get(i));
        }
        State newState = transition.getNewState();
        stateComboBox.setSelectedItem(stateRegister.getStates().indexOf(newState));
        // (->q1/q2/q3/q4, B, R)
        JComboBox symbolComboBox = new JComboBox();
        for(Character alphabetChar: tapeAlphabet){
            symbolComboBox.addItem(alphabetChar);
        }

        JComboBox moveComboBox = new JComboBox();
        for (Move move: Move.values()) {
            moveComboBox.addItem(move.toString());
        }

        panel.add(stateComboBox);
        panel.add(symbolComboBox);
        panel.add(moveComboBox);
    }

}
