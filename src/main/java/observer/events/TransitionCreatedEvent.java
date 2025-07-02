package observer.events;

import core.Transition;
import observer.Event;

public record TransitionCreatedEvent(Transition transition) implements Event {
}
