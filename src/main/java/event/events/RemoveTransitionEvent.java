package event.events;

import core.State;
import core.Transition;
import event.Event;

public record RemoveTransitionEvent(State state, Character symbol) implements Event {
}
