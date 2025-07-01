package event.events;

import event.Event;

public record RemoveSymbolFromTapeAlphabetEvent(Character symbol) implements Event {
}
