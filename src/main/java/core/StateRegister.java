package core;

import lombok.Getter;
import observer.Publisher;
import observer.events.AddStateEvent;
import observer.events.RemoveStateEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StateRegister implements IStateRegister {
    private final Publisher<AddStateEvent> addStatePublisher = new Publisher<>();
    private final Publisher<RemoveStateEvent> removeStatePublisher = new Publisher<>();

    private final List<State> states = new ArrayList<>();

    public void addState(int index, State state) {
        this.states.add(index, state);
        this.addStatePublisher.publish(new AddStateEvent(index, state));
    }

    public void removeState(int index, State state) {
        this.states.remove(index);
        this.removeStatePublisher.publish(new RemoveStateEvent(index, state));
    }

    @Override
    public List<Character> getSymbols() {
        List<Character> symbols = new ArrayList<>();
        for (State state : this.states) {
            symbols.addAll(state.getTransitions().keySet());
        }
        return symbols;
    }

}
