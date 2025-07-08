package core;

import lombok.Getter;
import observer.Publisher;
import observer.events.AddStateEvent;
import observer.events.RemoveStateEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Central registry for managing states in a Turing machine.
 * 
 * <p>The StateRegister maintains an ordered list of states and provides methods for
 * adding, removing, and querying states. It ensures referential integrity by updating
 * transition references when states are removed.</p>
 * 
 * <p>The registry publishes events when states are added or removed, allowing the UI
 * and other components to stay synchronized with changes.</p>
 * 
 * @see State
 * @see Transition
 */
@Getter
public class StateRegister {
    private final Publisher<AddStateEvent> addStatePublisher = new Publisher<>();
    private final Publisher<RemoveStateEvent> removeStatePublisher = new Publisher<>();

    private final List<State> states;


    public StateRegister(List<State> states){
        this.states = states;
    }
    /**
     * Adds a new state to the register at the specified index.
     * 
     * <p>Creates a new state with no transitions and non-terminating status.
     * If the index is beyond the current size, the state is appended to the end.
     * Publishes an AddStateEvent to notify observers of the change.</p>
     * 
     * @param index the position where the new state should be inserted
     */
    public void addState(int index) {
        State state = new State(new HashMap<>(), false);
        System.out.println("Adding new state at index: " + index);
        if(index < states.size()){
            this.states.add(index, state);
        }
        else{
            this.states.add(state);
        }
        this.addStatePublisher.publish(new AddStateEvent(index, state));
    }

    /**
     * Removes a state from the register and updates all references.
     * 
     * <p>This method not only removes the state from the register but also
     * ensures referential integrity by setting all transition references
     * to the removed state to null. This prevents dangling references
     * that could cause runtime errors.</p>
     * 
     * <p>Publishes a RemoveStateEvent to notify observers of the change.</p>
     * 
     * @param state the state to remove from the register
     */
    public void removeState(State state) {
        this.states.remove(state);
        for (State registerState : this.getStates()) {
            if(registerState.getTransitions() == null) {
                continue;
            }
            for (Transition transition : registerState.getTransitions().values()) {
                if (transition.getNewState().equals(state)) {
                    transition.setNewState(null);
                }
            }
        }
        this.removeStatePublisher.publish(new RemoveStateEvent(state));
    }

    /**
     * Finds the state that contains the specified transition.
     * 
     * <p>This method searches through all states in the register to find
     * which state contains the given transition object. This is useful
     * for reverse lookups when you have a transition and need to know
     * its containing state.</p>
     * 
     * @param transition the transition to search for
     * @return the state containing the transition, or null if not found
     */
    public State getState(Transition transition) {
        for (State state : this.states) {
            if (state.getTransitions().containsValue(transition)) {
                return state;
            }
        }
        return null;
    }

    public State getState(State state){
        for (State s : this.states) {
            if (s.equals(state)) {
                return s;
            }
        }
        return null;
    }

    public int indexOf(State state) {
        return this.states.indexOf(state);
    }

    public List<Character> getSymbols() {
        Set<Character> symbols = new HashSet<>();

        for (State state : this.states) {
            symbols.addAll(state.getTransitions().keySet());
        }
        
        return symbols.stream().toList();
    }
}
