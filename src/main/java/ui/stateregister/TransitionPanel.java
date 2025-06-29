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


    public TransitionPanel (StateRegister stateRegister, Set<Character> tapeAlphabet, Transition transition){
        super();
        this.stateRegister = stateRegister;
        this.tapeAlphabet = tapeAlphabet;
        JPanel panel = new JPanel();

        JComboBox<String> stateComboBox = new JComboBox<>();
        for (int i = 0; i < stateRegister.getStates().size(); i++) {
            stateComboBox.addItem("q" + i);
        }
        State newState = transition.getNewState();
        stateComboBox.setSelectedItem("q" + stateRegister.getStates().indexOf(newState));

        // (->q1/q2/q3/q4, B, R)
        JComboBox<Character> symbolComboBox = new JComboBox();
        for(Character alphabetChar: tapeAlphabet){
            symbolComboBox.addItem(alphabetChar);
        }
        symbolComboBox.setSelectedItem(transition.getNewSymbol());

        JComboBox<String> moveComboBox = new JComboBox();
        for (Move move: Move.values()) {
            moveComboBox.addItem(move.toString());
        }
        moveComboBox.setSelectedItem(transition.getMove().toString());
        this.add(new JLabel(("(")));
        this.add(stateComboBox);
        this.add(symbolComboBox);
        this.add(moveComboBox);
        this.add(new JLabel((")")));
    }

    public TransitionPanel(StateRegister stateRegister, Set<Character> tapeAlphabet) {
        //implement events for adding a new transition
        super();
        this.stateRegister = stateRegister;
        this.tapeAlphabet = tapeAlphabet;
        this.add(new JLabel("to be implemented"));
    }
}
