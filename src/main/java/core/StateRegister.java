package core;

import lombok.Getter;
import observer.Publisher;
import observer.events.AddStateEvent;
import observer.events.RemoveStateEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class StateRegister {
    private final Publisher<AddStateEvent> addStatePublisher = new Publisher<>();
    private final Publisher<RemoveStateEvent> removeStatePublisher = new Publisher<>();

    private final List<State> states;


    public StateRegister(List<State> states){
        this.states = states;
    }
    public void addState(int index) {
        State state = new State(null, false);
        System.out.println("Adding new state at index: " + index);
        if(index < states.size()){
            this.states.add(index, state);
        }
        else{
            this.states.add(state);
        }
        this.addStatePublisher.publish(new AddStateEvent(index, state));
    }

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

    public List<Character> getSymbols() {
        Set<Character> symbols = new HashSet<>();

        for (State state : this.states) {
            symbols.addAll(state.getTransitions().keySet());
        }
        
        return symbols.stream().toList();
    }
}
