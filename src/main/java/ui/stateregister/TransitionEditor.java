package ui.stateregister;

import core.Configuration;
import core.StateRegister;
import core.Transition;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class TransitionEditor extends AbstractCellEditor implements TableCellEditor {
    private TransitionPanel transitionPanel;
    private Transition currentTransition;

    private final StateRegister stateRegister;
    private final Configuration configuration;

    public TransitionEditor(StateRegister stateRegister, Configuration configuration) {
        this.stateRegister = stateRegister;
        this.configuration = configuration;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Transition) {
            currentTransition = (Transition) value;
            transitionPanel = new TransitionPanel(stateRegister, configuration.getTapeAlphabet(), currentTransition);
            return transitionPanel;
        }
        return new TransitionPanel(stateRegister, configuration.getTapeAlphabet());
    }

    @Override
    public Object getCellEditorValue() {
        // You may want to extract the edited values from transitionPanel here
        return currentTransition;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}