package core;

import java.util.Map;

/**
 * Class representing a state in a Turing machine.
 * A state consists of transitions for each character, and a flag indicating if it is a terminating state.
 */
public class State {
    private Map<Character, Transition> transitions;
    private boolean terminates;
}
