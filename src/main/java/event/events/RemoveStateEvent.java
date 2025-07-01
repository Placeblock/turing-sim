package event.events;

import core.State;
import event.Event;

public record RemoveStateEvent(State state) implements Event {
}
