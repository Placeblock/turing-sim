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
        TapeCell<Character> head = this.machineState.getTape().getHead();
        Character headSymbol = head.getSymbol();
        Transition transition = this.machineState.getCurrentState().getTransition(headSymbol);
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
        }
    }
}
