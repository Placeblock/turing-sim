package ui.stateregister;

import core.Configuration;
import core.StateRegister;
import event.Emitter;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;



public class StateRegisterUI extends JTable {
    private final Emitter<AddStateEvent> addStatePublisher;
    private final Emitter<RemoveStateEvent> removeStatePublisher;

    private final StateRegister stateRegister;
    private final Configuration configuration;

    public StateRegisterUI(StateRegister stateRegister, Configuration configuration,
                           Emitter<AddStateEvent> addStatePublisher,
                           Emitter<RemoveStateEvent> removeStatePublisher) {
        super(new StateRegisterTableModel(stateRegister, configuration));
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }
        this.addStatePublisher = addStatePublisher;
        this.removeStatePublisher = removeStatePublisher;
        this.stateRegister = stateRegister;
        this.configuration = configuration;

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
        
        return new TransitionRenderer(this.stateRegister, this.configuration);
    }


}
