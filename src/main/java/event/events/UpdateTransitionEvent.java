package event.events;

import core.State;
import core.Transition;
import event.Event;

public record UpdateTransitionEvent(State state, char symbol, Transition transition) implements Event {

}
