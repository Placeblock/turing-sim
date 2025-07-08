package core;

import lombok.Getter;
import lombok.Setter;
import observer.Publisher;
import observer.events.TransitionCreatedEvent;
import observer.events.TransitionRemovedEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a state in a Turing machine.
 *
 * <p>A state consists of a set of transitions that define how the machine should behave
 * when reading different symbols from the tape. Each state can be marked as terminating,
 * which means the machine will stop execution when it reaches this state.</p>
 *
 * <p>States publish events when transitions are created or removed, allowing observers
 * to react to changes in the state's configuration.</p>
 *
 * @see Transition
 * @see StateRegister
 */
@Getter
public class State {
    private final Map<Character, Transition> transitions;
    @Setter
    private boolean terminates;
    private final Publisher<TransitionCreatedEvent> transitionCreatedPublisher = new Publisher<>();
    private final Publisher<TransitionRemovedEvent> transitionRemovedPublisher = new Publisher<>();

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

    public void removeTransition(Character symbol) {
        System.out.println("REMOVED TRANSITION");
        Transition transition = this.transitions.remove(symbol);
        this.transitionRemovedPublisher.publish(new TransitionRemovedEvent(transition));
    }

    public Character getSymbol(Transition transition) {
        for (Character symbol : this.transitions.keySet()) {
            if (this.transitions.get(symbol).equals(transition)) {
                return symbol;
            }
        }
        return null;
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
