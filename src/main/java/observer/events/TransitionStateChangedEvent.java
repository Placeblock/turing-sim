package observer.events;

import core.State;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import observer.Event;

@Getter
@RequiredArgsConstructor
public class TransitionStateChangedEvent implements Event {
    private final State newState;
}
