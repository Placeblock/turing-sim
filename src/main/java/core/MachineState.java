package core;


import core.tape.Tape;

/**
 * Class representing the state of the Turing machine itself.
 * It contains the current position of the tape of the machine.
 */
public class MachineState {
    private Tape<Character> tape;
    private State currentState;
}
