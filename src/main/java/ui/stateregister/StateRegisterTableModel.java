package ui.stateregister;


import core.StateRegister;
import core.State;
import core.Transition;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
public class StateRegisterTableModel extends AbstractTableModel {
    private StateRegister stateRegister;
    private LinkedHashSet<Character> eingabeAlphabet;

    @Override
    public int getRowCount() {
        return this.stateRegister.getStates().size();
    }

    @Override
    public int getColumnCount() {
        return this.stateRegister.getSymbols().size()+1;
    }

    @Override
    public Object getValueAt(int x, int y) {
        if (y == 0) {
            return "q" + x;
        }
        if (x == 0){
            assert eingabeAlphabet != null;
            Iterator<Character> iterator = eingabeAlphabet.iterator();
            Character symbol = null;
            for (int i = 0; i < y; i++) {
                if (iterator.hasNext()) {
                    symbol = iterator.next();
                } else {
                    throw new IndexOutOfBoundsException("index bigger than rows");
                }
            }
            return "q" + symbol;
        }
        State stateOfTransition = stateRegister.getStates().get(x);
        Transition transition = stateOfTransition.getTransitions().get(y);

        return transition;
    }

    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        } else {
            return JLabel.class;
        }
    }

}