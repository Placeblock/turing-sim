package event.events;

import event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TapeSymbolsChangeEvent implements Event {
    private final Set<Character> symbols;
}
