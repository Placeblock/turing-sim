package ui.stateregister;

import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import core.State;
import core.StateRegister;
import core.Transition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StateRegisterTableModel extends AbstractTableModel {

    private StateRegister stateRegister;
    private LinkedHashSet<Character> eingabeAlphabet;

    @Override
    public int getRowCount() {
        return this.stateRegister.getStates().size() + 1;
    }

    @Override
    public int getColumnCount() {
        return eingabeAlphabet.size() + 1;
    }

    @Override
    public Object getValueAt(int x, int y) {
        if (y == 0 && x != 0) {
            return "q" + (x - 1);
        }
        if (x == 0) {
            assert eingabeAlphabet != null;
            Iterator<Character> iterator = eingabeAlphabet.iterator();
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
