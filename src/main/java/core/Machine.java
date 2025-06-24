package core;

import core.tape.TapeCell;
import lombok.RequiredArgsConstructor;

/**
 * Class representing a Turing machine.
 */
@RequiredArgsConstructor
public class Machine {
    private final MachineState machineState;

    /**
     * Executes a single step of the Turing machine.
     * It reads the current state and the symbol under the head,
     * applies the transition defined for that state and symbol,
     * writes the new symbol to the tape,
     * moves the head according to the transition,
     * and updates the current state of the machine.
     * @return true if the step was successful, false if the machine is in a terminating state.
     */
    public boolean step() {
        var state = this.machineState.getCurrentState();

        if (state.isTerminates()) {
            return false;
        }

        TapeCell<Character> head = this.machineState.getTape().getHead();
        Character currentHeadSymbol = head.getSymbol();
        Transition transition = state.getTransition(currentHeadSymbol);
        if (transition == null) {
            throw new IllegalStateException("No transition found for symbol " + currentHeadSymbol);
        }

        head.setSymbol(transition.getNewSymbol());
        this.machineState.setCurrentState(transition.getNewState());

        switch (transition.getMove()) {
            case LEFT:
                this.machineState.getTape().movePrevious();
                break;
            case RIGHT:
                this.machineState.getTape().moveNext();
                break;
            case NONE: // to shut up the compiler
                // Do nothing, stay on the same cell
                break;
        }

        return true;
    }
}
