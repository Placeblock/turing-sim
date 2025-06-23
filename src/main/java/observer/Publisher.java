package observer;

import java.util.HashSet;
import java.util.Set;

public class Publisher<T extends Event> {

    private final Set<Observer<T>> observers = new HashSet<>();

    void publish(T t) {
        for (Observer<T> observer : this.observers) {
            observer.onEvent(t);
        }
    }

    void subscribe(Observer<T> observer) {
        this.observers.add(observer);
    }

    void unsubscribe(Observer<T> observer) {
        this.observers.remove(observer);
    }

}
