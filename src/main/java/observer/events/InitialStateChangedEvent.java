package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import observer.Event;

@Getter
@RequiredArgsConstructor
public class InitialStateChangedEvent implements Event {
    private final String initialState;
}
