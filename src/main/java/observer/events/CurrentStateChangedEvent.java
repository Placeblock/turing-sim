package observer.events;

import core.State;
import observer.Event;

public record CurrentStateChangedEvent(State currentState) implements Event {  
}
