package event.events;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveStateEvent {
    private final int index;
}
