package core;

import lombok.Getter;
import observer.Publisher;
import observer.events.TransitionCreatedEvent;

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
    private final Publisher<TransitionCreatedEvent> transitionCreatedPublisher = new Publisher<>();

    public State(Map<Character, Transition> transitions, boolean terminates) {
        this.transitions = transitions;
        this.terminates = terminates;
    }

    public Transition getTransition(char c) {
        return transitions.get(c);
    }

    public void addTransition(Character symbol, Transition transition) {
        this.transitions.put(symbol, transition);
        this.transitionCreatedPublisher.publish(new TransitionCreatedEvent(transition));
    }

    public void updateSymbols(Set<Character> symbols) {
        for (Transition transition : this.transitions.values()) {
            if (!symbols.contains(transition.getNewSymbol())) {
                System.out.println("Reset Symbol in Transition");
                transition.setNewSymbol(null);
            }
        }
        HashSet<Character> removed = new HashSet<>(this.transitions.keySet());
        removed.removeAll(symbols);
        for (Character symbol : removed) {
            this.transitions.remove(symbol);
        }
    }
}
