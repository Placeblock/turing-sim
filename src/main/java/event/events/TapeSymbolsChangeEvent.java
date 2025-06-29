package event.events;

import event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;

@Getter
@RequiredArgsConstructor
public class TapeSymbolsChangeEvent implements Event {
    private final LinkedHashSet<Character> symbols;
}
