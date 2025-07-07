package core.tape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import observer.Publisher;
import observer.events.TapeHeadPositionChangedEvent;
import observer.events.TapeLengthModifiedEvent;
import observer.events.TapeResetEvent;

public class Tape<T> {

    private final T defaultSymbol;
    private TapeCell<T> headPosition;

    @Getter
    private final Publisher<TapeHeadPositionChangedEvent<T>> headPositionChangedPublisher = new Publisher<>();
    @Getter
    private final Publisher<TapeLengthModifiedEvent> lengthModifiedPublisher = new Publisher<>();
    @Getter
    private final Publisher<TapeResetEvent> resetPublisher = new Publisher<>();

    public Tape(T defaultSymbol) {
        this.defaultSymbol = defaultSymbol;
        this.headPosition = new TapeCell<>(defaultSymbol);
    }

    /**
     * Can be used to initialize a Tape using a list of symbols.
     * It uses an iterator to create a TapeCell using the first symbol,
     * creates cells for subsequent symbols and connects them to the head.
     */
    public Tape(T defaultSymbol, List<T> symbols) {
        this.defaultSymbol = defaultSymbol;
        if (symbols.isEmpty()) {
            this.headPosition = new TapeCell<>(defaultSymbol);
            return;
        }

        Iterator<T> iterator = symbols.iterator();
        this.headPosition = new TapeCell<>(iterator.next());

        TapeCell<T> lastCell = this.headPosition;
        while (iterator.hasNext()) {
            TapeCell<T> cell = new TapeCell<>(iterator.next());
            cell.connectPrevious(lastCell);
            lastCell = cell;
        }
    }

    public TapeCell<T> getHead() {
        return this.headPosition;
    }

    public void setHead(TapeCell<T> head) {
        this.headPosition = head;
        this.headPositionChangedPublisher.publish(new TapeHeadPositionChangedEvent<>(head));
    }

    public void reset(TapeCell<T> newHead) {
        this.headPosition = newHead;
        this.resetPublisher.publish(new TapeResetEvent());
    }

    public TapeCell<T> getNext() {
        if (this.headPosition.getNext() == null) {
            TapeCell<T> next = new TapeCell<>(defaultSymbol);
            this.headPosition.connectNext(next);
            this.lengthModifiedPublisher.publish(new TapeLengthModifiedEvent());
        }
        return this.headPosition.getNext();
    }

    public TapeCell<T> getPrevious() {
        if (this.headPosition.getPrevious() == null) {
            TapeCell<T> previous = new TapeCell<>(defaultSymbol);
            this.headPosition.connectPrevious(previous);
            this.lengthModifiedPublisher.publish(new TapeLengthModifiedEvent());
        }
        return this.headPosition.getPrevious();
    }

    public List<TapeCell<T>> getNext(int count) {
        List<TapeCell<T>> cells = new ArrayList<>();

        TapeCell<T> current = this.headPosition;
        for (int i = 0; i < count; i++) {
            cells.add(current = current.getNext());
        }

        return cells;
    }

    public List<TapeCell<T>> getPrevious(int count) {
        List<TapeCell<T>> cells = new ArrayList<>();

        TapeCell<T> current = this.headPosition;
        for (int i = 0; i < count; i++) {
            cells.add(current = current.getPrevious());
        }

        return cells;
    }

    public List<T> getAllData() {
        List<T> data = new ArrayList<>();
        TapeCell<T> current = this.headPosition;
        while (current != null) {
            data.addFirst(current.getSymbol());
            current = current.getPrevious();
        }
        current = this.headPosition.getNext();
        while (current != null) {
            data.add(current.getSymbol());
            current = current.getNext();
        }
        return data;
    }

    public List<TapeCell<T>> getAllCells() {
        var cells = new ArrayList<TapeCell<T>>();
        TapeCell<T> current = this.headPosition;
        while (current != null) {
            cells.addFirst(current);
            current = current.getPrevious();
        }
        current = this.headPosition.getNext();
        while (current != null) {
            cells.add(current);
            current = current.getNext();
        }
        return cells;
    }

    public TapeCell<T> moveNext() {
        this.setHead(this.getNext());
        return this.getHead();
    }

    public TapeCell<T> movePrevious() {
        this.setHead(this.getPrevious());
        return this.getHead();
    }
}
