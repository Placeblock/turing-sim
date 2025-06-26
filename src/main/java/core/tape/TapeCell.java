package core.tape;

import lombok.Getter;
import lombok.Setter;
import observer.Publisher;
import observer.events.CellSymbolChangedEvent;

/**
 * TapeCell implementation. This represents one cell on the tape.
 * It stores references to the next and previous Cell.
 * It can automatically create and link new cells.
 * @param <T>
 */
@Getter
@Setter
public class TapeCell<T> {
    /**
     * The value of this cell
     */
    private T symbol;
    /**
     * Reference of the previous element
     */
    private TapeCell<T> previous = null;
    /**
     * Reference of the next element
     */
    private TapeCell<T> next = null;

    private Publisher<CellSymbolChangedEvent<T>> symbolChangedPublisher = new Publisher<>();

    public TapeCell(T symbol) {
        this.symbol = symbol;
    }

    public void connectPrevious(TapeCell<T> previous) {
        this.previous = previous;
        previous.setNext(this);
    }
    public void connectNext(TapeCell<T> next) {
        this.next = next;
        next.setPrevious(this);
    }

    public void setSymbol(T symbol) {
        this.symbol = symbol;
        this.symbolChangedPublisher.publish(new CellSymbolChangedEvent<>(symbol));
    }
}
