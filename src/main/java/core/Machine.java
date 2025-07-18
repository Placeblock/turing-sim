package core;


import core.tape.TapeCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

/**
 * Class representing a Turing machine.
 */
@RequiredArgsConstructor
public class Machine {
    @Getter
    private final MachineState machineState;

    /**
     * Executes a single step of the Turing machine.
     * It reads the current state and the symbol under the head,
     * applies the transition defined for that state and symbol,
     * writes the new symbol to the tape,
     * moves the head according to the transition,
     * and updates the current state of the machine.
     * @return true if the step was successful, false if the machine is in a terminating state.
     * @throws IllegalStateException if no transition exists for the current state/symbol
     *                              or if the transition is incomplete
     */
    public boolean step() {
        var state = this.machineState.getCurrentState();

        if (state.isTerminates()) {
            return false;
        }

        TapeCell<Character> head = this.machineState.getTape().getHead();
        Character currentHeadSymbol = head.getSymbol();
        System.out.println("Reading: " + currentHeadSymbol);
        Transition transition = state.getTransition(currentHeadSymbol);
        if (transition == null) {
            JOptionPane.showMessageDialog(null,"No transition found for symbol " + currentHeadSymbol + ". \nYour table seems incomplete.","Fehler!", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("No transition found for symbol " + currentHeadSymbol);
        }
        if (transition.getNewSymbol() == null || transition.getNewState() == null) {
            throw new IllegalStateException("Incomplete State reached! (No State or Symbol set)");
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

        System.out.println("Tape content: " + this.machineState.getTape().getAllData());

        return true;
    }
}
