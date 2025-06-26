package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InitialStateChangedEvent {
    private final String initialState;
}
