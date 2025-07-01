package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import observer.Event;

@Getter
@RequiredArgsConstructor
public class TransitionSymbolChangedEvent implements Event {
    private final Character newSymbol;
}
