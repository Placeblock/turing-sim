package core;

import core.tape.TapeCell;
import lombok.RequiredArgsConstructor;

/**
 * Class representing a Turing machine.
 */
@RequiredArgsConstructor
public class Machine {
    private final MachineState machineState;

    public void step() {
        var state = this.machineState.getCurrentState();

        if (state.isTerminates()) {
            // TODO terminate
            return;
        }

        TapeCell<Character> head = this.machineState.getTape().getHead();
        Character headSymbol = head.getSymbol();
        Transition transition = state.getTransition(headSymbol);
        if (transition == null) {
            throw new IllegalStateException("No transition found for symbol " + headSymbol);
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
    }
}
