package observer.events;

import core.State;
import observer.Event;

public record RemoveStateEvent(int index, State state) implements Event {
}
