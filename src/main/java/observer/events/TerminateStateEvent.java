package observer.events;


import core.State;
import observer.Event;

public record TerminateStateEvent(State state, boolean terminates) implements Event {
}
