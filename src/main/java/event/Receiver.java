package event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Central event receiver that implements a type-safe event dispatching system.
 * 
 * <p>The Receiver class provides a mechanism for registering event handlers
 * and dispatching events to the appropriate handlers based on event type.
 * It maintains a registry of handlers organized by event class and ensures
 * type safety through generics.</p>
 * 
 * <p>This class is fundamental to the application's event-driven architecture,
 * allowing loose coupling between components while maintaining type safety.</p>
 * 
 * @see Handler
 * @see Event
 * @see Emitter
 */
public class Receiver {
    private final Map<Class<? extends Event>, Set<Handler<?>>> handlers = new HashMap<>();

    public <E extends Event> void receive(E event) {
        Class<? extends Event> eventClass = event.getClass();
        Set<Handler<?>> handlers = this.handlers.get(eventClass);
        if (handlers == null) return;

        for (Handler<?> handler : handlers) {
            /*
                After research from time to time over the last years I came to the conclusion
                that Observer / Event pattern without at least one unsafe cast is not possible.
             */
            //noinspection unchecked
            ((Handler<E>) handler).handle(event);
        }
    }

    public <E extends Event> void registerHandler(Class<E> eventClass, Handler<E> handler) {
        this.handlers.computeIfAbsent(eventClass, k -> new HashSet<>()).add(handler);
    }

    public <E extends Event> void unregisterHandler(Class<E> eventClass, Handler<E> handler) {
        if (!this.handlers.containsKey(eventClass)) {
            throw new IllegalArgumentException("No handler for event " + eventClass.getName());
        }
        
        this.handlers.get(eventClass).remove(handler);
    }
}
