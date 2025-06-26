package event.events;

import event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BlankSymbolChangeEvent implements Event {
    private final Character symbol;
}
