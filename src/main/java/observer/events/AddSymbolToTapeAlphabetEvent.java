package observer.events;

import observer.Event;

public record AddSymbolToTapeAlphabetEvent(Character symbol) implements Event {
}
