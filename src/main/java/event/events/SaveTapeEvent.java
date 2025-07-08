package event.events;

import java.io.File;

import event.Event;

public record SaveTapeEvent(File file) implements Event {
}
