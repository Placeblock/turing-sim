package core;

/**
 * Class representing a transition in a Turing machine.
 * A transition consists of a new state, a new symbol to write, and a move direction.
 */
public class Transition {
    private final char newSymbol;
    private final Move move;
    private final State newState;

    public Transition(char newSymbol, Move move, State newState) {
        this.newSymbol = newSymbol;
        this.move = move;
        this.newState = newState;
    }

    public char getNewSymbol() {
        return newSymbol;
    }

    public Move getMove() {
        return move;
    }

    public State getNewState() {
        return newState;
    }
}
