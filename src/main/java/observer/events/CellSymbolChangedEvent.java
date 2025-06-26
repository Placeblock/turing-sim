package observer.events;

import observer.Event;

public record CellSymbolChangedEvent<T>(T newSymbol) implements Event {
}
