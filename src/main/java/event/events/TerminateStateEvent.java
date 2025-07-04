package event.events;

import core.State;
import event.Event;

public record TerminateStateEvent(State state, boolean terminates) implements Event {
}
