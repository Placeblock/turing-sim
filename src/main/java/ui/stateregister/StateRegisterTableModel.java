package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class StateRegisterTableModel extends AbstractTableModel {

    private final StateRegister stateRegister;
    private final Configuration configuration;

    public StateRegisterTableModel(StateRegister stateRegister, Configuration configuration) {
        this.stateRegister = stateRegister;
        this.configuration = configuration;
    }

    @Override
    public int getRowCount() {
        return this.stateRegister.getStates().size() + 1;
    }

    @Override
    public int getColumnCount() {
        System.out.println(this.configuration.getTapeAlphabet().size());
        return configuration.getTapeAlphabet().size() + 1;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Make only transition cells editable (not headers)
        return rowIndex > 0 && columnIndex > 0;
    }

    @Override
    public Object getValueAt(int x, int y) {
        if (y == 0 && x != 0) {
            return stateRegister.getStates().get(x - 1);
        }
        Character symbol = configuration.getTapeSymbol(y - 1);
        if (x == 0 && y != 0) {
            System.out.println(y-1);
            return symbol;
        }
        if (x == 0) return null;

        State state = stateRegister.getStates().get(x - 1);
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
