package core;


import core.tape.Tape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the state of the Turing machine itself.
 * It contains the current position of the tape of the machine.
 */
@Getter
@AllArgsConstructor
public class MachineState {
    private final Tape<Character> tape;
    @Setter
    private State currentState;
}
