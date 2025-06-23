package observer.events;

import core.State;
import observer.Event;

public record AddStateEvent(int index, State state) implements Event {
}
