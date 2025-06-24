package ui.stateregister;

import core.IStateRegister;
import event.Emitter;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class StateRegisterUI extends JTable {
    private final Emitter<AddStateEvent> addStatePublisher;
    private final Emitter<RemoveStateEvent> removeStatePublisher;

    private final IStateRegister stateRegister;

    public StateRegisterUI(IStateRegister stateRegister,
                           Emitter<AddStateEvent> addStatePublisher,
                           Emitter<RemoveStateEvent> removeStatePublisher) {
        super(new StateRegisterTableModel(stateRegister));
        this.addStatePublisher = addStatePublisher;
        this.removeStatePublisher = removeStatePublisher;
        this.stateRegister = stateRegister;

        this.stateRegister.getAddStatePublisher().subscribe(this::onStateAdd);
        this.stateRegister.getRemoveStatePublisher().subscribe(this::onStateRemove);
    }

    public void onStateAdd(observer.events.AddStateEvent event) {
        // Add UI State
    }
    public void onStateRemove(observer.events.RemoveStateEvent event) {
        // Remove UI State
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column){
        if (column == 0) {
            return new DefaultTableCellRenderer();
        }
        return new TransitionRenderer();
    }
}
