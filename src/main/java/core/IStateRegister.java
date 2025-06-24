package core;

import observer.Publisher;
import observer.events.AddStateEvent;
import observer.events.RemoveStateEvent;

import java.util.List;

public interface IStateRegister {
    void addState(int index, State state);
    void removeState(int index, State state);
    List<Character> getSymbols();
    List<State> getStates();

    Publisher<AddStateEvent> getAddStatePublisher();
    Publisher<RemoveStateEvent> getRemoveStatePublisher();

}
