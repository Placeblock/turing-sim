package ui.stateregister;

import core.*;
import event.Receiver;
import event.events.TransitionChangeEvent;
import event.events.TransitionCreateEvent;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.EventObject;

@RequiredArgsConstructor
public class TransitionEditor extends AbstractCellEditor implements TableCellEditor {
    private Transition transition;

    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;

    private final State state;
    private final Character symbol;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        transition = (Transition) value;
        boolean create = this.transition == null;
        if (this.transition == null) {
            this.transition = new Transition(null, Move.NONE, null);
        }
        Transition oldTransition = new Transition(transition.getNewSymbol(), transition.getMove(), transition.getNewState());
        this.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent changeEvent) {
                if (create) {
                    TransitionCreateEvent event = new TransitionCreateEvent(TransitionEditor.this.state, TransitionEditor.this.symbol, TransitionEditor.this.transition);
                    TransitionEditor.this.receiver.receive(event);
                } else {
                    TransitionChangeEvent event = new TransitionChangeEvent(oldTransition, TransitionEditor.this.transition);
                    TransitionEditor.this.receiver.receive(event);
                }
            }

            @Override
            public void editingCanceled(ChangeEvent changeEvent) {

            }
        });
        return new TransitionPanel(this.stateRegister, this.configuration, this.transition);
    }

    @Override
    public Object getCellEditorValue() {
        // You may want to extract the edited values from transitionPanel here
        return transition;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}