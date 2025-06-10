package core;

/**
 * Class representing a transition in a Turing machine.
 * A transition consists of a new state, a new symbol to write, and a move direction.
 */
public class Transition {
    private State newState;
    private char newSymbol;
    private Move move;
}
