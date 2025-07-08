package core;

import core.tape.Tape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import observer.Publisher;
import observer.events.CurrentStateChangedEvent;

/**
 * Represents the runtime state of a Turing machine.
 * 
 * <p>The MachineState encapsulates the current execution state of the machine,
 * including the tape and the current state. It provides a centralized way to
 * track and modify the machine's state during execution.</p>
 * 
 * <p>When the current state changes, this class publishes events to notify
 * observers such as the UI components that need to update their display.</p>
 * 
 * @see Machine
 * @see State
 * @see Tape
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
