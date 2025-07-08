package event.events;

import java.io.File;

import event.Event;

public record SaveTransitionsEvent(File file) implements Event {
}
