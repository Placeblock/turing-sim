package observer.events;

import core.State;
import observer.Event;

public record RemoveStateEvent(State state) implements Event {
}
