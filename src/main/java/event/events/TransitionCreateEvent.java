package event.events;

import core.State;
import core.Transition;
import event.Event;

public record TransitionCreateEvent(State state, Character symbol, Transition transition) implements Event {

}
