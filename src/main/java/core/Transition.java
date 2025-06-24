package core;

import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a transition in a Turing machine.
 * A transition consists of a new state, a new symbol to write, and a move direction.
 */
@Getter
public class Transition {
    private final char newSymbol;
    private final Move move;
    @Setter
    private State newState;

    public Transition(char newSymbol, Move move, State newState) {
        this.newSymbol = newSymbol;
        this.move = move;
        this.newState = newState;
    }
}
