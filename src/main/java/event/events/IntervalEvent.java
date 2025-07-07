package event.events;

import event.Event;

public record IntervalEvent(int interval) implements Event {
}
