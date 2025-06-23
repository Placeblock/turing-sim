package event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
