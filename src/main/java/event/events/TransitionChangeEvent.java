package event.events;

import core.Transition;
import event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransitionChangeEvent implements Event {
    private final Transition oldTransition; // Reference to the old one
    private final Transition newTransition; // Data for the updated one
}
