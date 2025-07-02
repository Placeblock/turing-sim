package ui.stateregister;

import core.Configuration;
import core.Move;
import core.StateRegister;
import core.Transition;
import event.Receiver;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class TransitionEditor extends AbstractCellEditor implements TableCellEditor {
    private Transition currentTransition;

    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;

    public TransitionEditor(Receiver receiver, StateRegister stateRegister, Configuration configuration) {
        this.receiver = receiver;
        this.stateRegister = stateRegister;
        this.configuration = configuration;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentTransition = (Transition) value;
        if (this.currentTransition == null) {
            this.currentTransition = new Transition(null, Move.NONE, null);
        }
        return new TransitionPanel(this.receiver, this.stateRegister, this.configuration, this.currentTransition);
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