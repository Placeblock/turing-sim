package ui.stateregister;

import core.State;
import core.StateRegister;
import core.Transition;

import javax.swing.*;
import java.util.Set;

public class TransitionRenderTextField extends JTextField {
    public TransitionRenderTextField(StateRegister stateRegister, Set<Character> tapeAlphabet, Transition transition){
        State newState = transition.getNewState();
        String state = String.valueOf(stateRegister.getStates().indexOf(newState));
        String newTransition = String.valueOf(transition.getNewSymbol());
        String move = transition.getMove().toString();
        String text = String.format("(%s, %s, %s)", state, newTransition, move);
        this.setText(text);
    }
}
