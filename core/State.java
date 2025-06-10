package core;

import java.util.Map;

public class State {
    private Map<Character, Transition> transitions;
    private boolean terminates;
}
