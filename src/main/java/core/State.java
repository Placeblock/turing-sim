package core;

import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class representing a state in a Turing machine.
 * A state consists of transitions for each character, and a flag indicating if it is a terminating state.
 */
@Getter
public class State {
    private final Map<Character, Transition> transitions;
    private final boolean terminates;

    public State(Map<Character, Transition> transitions, boolean terminates) {
        this.transitions = transitions;
        this.terminates = terminates;
    }

    public Transition getTransition(char c) {
        return transitions.get(c);
    }

    public Set<Character> getSymbols() {
        return new HashSet<>(this.transitions.keySet());
    }

    public void removeSymbol(Character symbol) {
        Transition transition = transitions.remove(symbol);
        if (transition != null && transition.getNewSymbol() == symbol) {
            transition.setNewSymbol(null);
        }
    }
}
