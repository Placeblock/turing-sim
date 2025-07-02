package core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import observer.Publisher;
import observer.events.TransitionMoveChangedEvent;
import observer.events.TransitionStateChangedEvent;
import observer.events.TransitionSymbolChangedEvent;

import java.util.Set;

/**
 * Class representing a transition in a Turing machine.
 * A transition consists of a new state, a new symbol to write, and a move direction.
 */
@Getter
@Setter
public class Transition {
    private final Publisher<TransitionSymbolChangedEvent> symbolChangedPublisher = new Publisher<>();
    private final Publisher<TransitionMoveChangedEvent> moveChangedPublisher = new Publisher<>();
    private final Publisher<TransitionStateChangedEvent> stateChangedPublisher = new Publisher<>();

    private Character newSymbol;
    private Move move;
    private State newState;

    public Transition(Character newSymbol, Move move, State newState) {
        this.newSymbol = newSymbol;
        this.move = move;
        this.newState = newState;
    }

    public void setNewSymbol(Character newSymbol) {
        this.newSymbol = newSymbol;
        TransitionSymbolChangedEvent event = new TransitionSymbolChangedEvent(newSymbol);
        this.symbolChangedPublisher.publish(event);
    }

    public void setMove(Move move) {
        this.move = move;
        TransitionMoveChangedEvent event = new TransitionMoveChangedEvent(move);
        this.moveChangedPublisher.publish(event);
    }

    public void setNewState(State newState) {
        this.newState = newState;
        TransitionStateChangedEvent event = new TransitionStateChangedEvent(newState);
        this.stateChangedPublisher.publish(event);
    }
}
