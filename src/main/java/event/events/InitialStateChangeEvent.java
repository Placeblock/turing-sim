package event.events;

import event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InitialStateChangeEvent implements Event {
    private final String initialState;
}
