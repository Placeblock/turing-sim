package observer;

@FunctionalInterface
public interface Observer<T extends Event> {
    void onEvent(T event);
}
