package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TapeSymbolsChangedEvent {
    private final Set<Character> tapeSymbols;
}
