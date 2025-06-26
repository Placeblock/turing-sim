package observer.events;

import core.tape.TapeCell;
import observer.Event;

public record TapeHeadPositionChangedEvent<T>(TapeCell<T> newHeadPosition) implements Event {
}
