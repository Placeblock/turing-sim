package observer.events;

import core.Transition;
import observer.Event;

public record TransitionRemovedEvent(Transition transition) implements Event {
}
