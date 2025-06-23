package core;

import lombok.Getter;

import java.util.Map;

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
}
