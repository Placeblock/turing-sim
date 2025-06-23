package event.events;

import event.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddStateEvent implements Event {
    private final int index;
}
