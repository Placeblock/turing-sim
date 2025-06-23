package observer;

import java.util.HashSet;
import java.util.Set;

public class Publisher<T extends Event> {

    private final Set<Observer<T>> observers = new HashSet<>();

    public void publish(T t) {
        for (Observer<T> observer : this.observers) {
            observer.onEvent(t);
        }
    }

    public void subscribe(Observer<T> observer) {
        this.observers.add(observer);
    }

    public void unsubscribe(Observer<T> observer) {
        this.observers.remove(observer);
    }

}
