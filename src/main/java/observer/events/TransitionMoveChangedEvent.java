package observer.events;

import core.Move;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import observer.Event;

@Getter
@RequiredArgsConstructor
public class TransitionMoveChangedEvent implements Event {
    private final Move newMove;
}
