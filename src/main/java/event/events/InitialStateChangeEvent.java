package event.events;

import core.State;
import event.Event;

public record InitialStateChangeEvent(State initialState) implements Event {
}
