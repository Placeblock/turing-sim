package event;

@FunctionalInterface
public interface Handler<T extends Event> {
    void handle(T event);
}
