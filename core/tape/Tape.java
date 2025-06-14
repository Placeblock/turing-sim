package core.tape;

import java.util.Iterator;
import java.util.List;

public class Tape<T> {

    private final T defaultSymbol;
    private TapeCell<T> headPosition;

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

    public TapeCell<T> getNext() {
        if (this.headPosition.getNext() == null) {
            TapeCell<T> next = new TapeCell<>(defaultSymbol);
            this.headPosition.connectNext(next);
        }
        return this.headPosition.getNext();
    }

    public TapeCell<T> getPrevious() {
        if (this.headPosition.getPrevious() == null) {
            TapeCell<T> previous = new TapeCell<>(defaultSymbol);
            this.headPosition.connectPrevious(previous);
        }
        return this.headPosition.getPrevious();
    }

    public TapeCell<T> moveNext() {
        this.headPosition = this.getNext();
        return this.getHead();
    }

    public TapeCell<T> movePrevious() {
        this.headPosition = this.getPrevious();
        return this.getHead();
    }
}
