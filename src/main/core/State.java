package main.core;

import java.util.Map;

/**
 * Class representing a state in a Turing machine.
 * A state consists of transitions for each character, and a flag indicating if it is a terminating state.
 */
public class State {
    private final Map<Character, Transition> transitions;
    private final boolean terminates;

    public State(Map<Character, Transition> transitions, boolean terminates) {
        this.transitions = transitions;
        this.terminates = terminates;
    }

    public Map<Character, Transition> getTransitions() {
        return transitions;
    }

    public boolean isTerminating() {
        return terminates;
    }
}
