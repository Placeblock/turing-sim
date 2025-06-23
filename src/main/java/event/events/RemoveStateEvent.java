package event.events;

import event.Event;

public record RemoveStateEvent(int index) implements Event {
}
