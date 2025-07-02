package event.events;

import event.Event;

public record SaveTransitionsEvent(String filePath) implements Event {
}
