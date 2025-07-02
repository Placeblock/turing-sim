package observer.events;

import core.State;
import observer.Event;

public record InitialStateChangedEvent(State initialState) implements Event {
}
