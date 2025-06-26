package event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InitialStateChangeEvent {
    private final String initialState;
}
