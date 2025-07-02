package event.events;

import event.Event;

public record SaveTapeEvent(String filePath) implements Event {
}
