package event.events;

import event.Event;

public record AddSymbolToTapeAlphabetEvent(Character symbol) implements Event {
}
