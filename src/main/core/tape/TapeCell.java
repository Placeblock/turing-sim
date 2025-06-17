package main.core.tape;

/**
 * TapeCell implementation. This represents one cell on the tape.
 * It stores references to the next and previous Cell.
 * It can automatically create and link new cells.
 * @param <T>
 */
public class TapeCell<T> {
    /**
     * The value of this cell
     */
    private final T symbol;
    /**
     * Reference of the previous element
     */
    private TapeCell<T> previous = null;
    /**
     * Reference of the next element
     */
    private TapeCell<T> next = null;

    public TapeCell(T symbol) {
        this.symbol = symbol;
    }

    public T getSymbol() {
        return this.symbol;
    }

    public TapeCell<T> getPrevious() {
        return this.previous;
    }

    public TapeCell<T> getNext() {
        return this.next;
    }

    private void setPrevious(TapeCell<T> previous) {
        this.previous = previous;
    }
    private void setNext(TapeCell<T> next) {
        this.next = next;
    }

    public void connectPrevious(TapeCell<T> previous) {
        this.previous = previous;
        previous.setNext(this);
    }
    public void connectNext(TapeCell<T> next) {
        this.next = next;
        next.setPrevious(this);
    }

}
