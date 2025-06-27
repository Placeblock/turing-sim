package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import observer.Event;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TapeSymbolsChangedEvent implements Event {
    private final Set<Character> symbols;
}
