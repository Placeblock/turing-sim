package ui.stateregister;


import core.IStateRegister;
import core.State;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

@RequiredArgsConstructor
public class StateRegisterTableModel extends AbstractTableModel {
    private final IStateRegister stateRegister;

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
            return x;
        }
        State state = this.stateRegister.getStates().get(x);
        Character symbol = this.stateRegister.getSymbols().get(y - 1);
        return state.getTransitions().get(symbol);
    }

    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        } else {
            return JLabel.class;
        }
    }
}