package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Iterator;

public class StateRegisterTableModel extends AbstractTableModel {

    private StateRegister stateRegister;
    private Configuration configuration;

    public StateRegisterTableModel(StateRegister stateRegister, Configuration configuration) {
        this.stateRegister = stateRegister;
        this.configuration = configuration;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return this.stateRegister.getStates().size() + 1;
    }

    @Override
    public int getColumnCount() {
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
        if (x == 0) {
            Iterator<Character> iterator = configuration.getTapeAlphabet().iterator();
            Character symbol = null;
            for (int i = 0; i < y; i++) {
                symbol = iterator.next();
            }
            return symbol;
        }
        Character symbol = (Character) getValueAt(0, y);
        State stateOfTransition = stateRegister.getStates().get(x - 1);
        Transition transition = stateOfTransition.getTransitions().get(symbol);

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
