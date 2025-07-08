package core;

import core.tape.Tape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import observer.Publisher;
import observer.events.CurrentStateChangedEvent;

/**
 * Class representing the state of the Turing machine itself.
 * It contains the current position of the tape of the machine.
 */
@Getter
@AllArgsConstructor
public class MachineState {
    private final Publisher<CurrentStateChangedEvent> currentStateChangedPublisher = new Publisher<>();

    private final Tape<Character> tape;
    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        this.currentStateChangedPublisher.publish(new CurrentStateChangedEvent(currentState));
    }
}
