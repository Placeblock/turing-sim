package ui.stateregister;

import core.Configuration;
import core.StateRegister;
import event.Emitter;
import event.Receiver;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class StateRegisterUI extends JTable {
    private final Emitter<AddStateEvent> addStatePublisher;
    private final Emitter<RemoveStateEvent> removeStatePublisher;

    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;

    private JPopupMenu statePopupMenu;

    public StateRegisterUI(Receiver receiver,
                           StateRegister stateRegister, Configuration configuration,
                           Emitter<AddStateEvent> addStatePublisher,
                           Emitter<RemoveStateEvent> removeStatePublisher) {
        super(new StateRegisterTableModel(stateRegister, configuration));
        this.receiver = receiver;

        this.setRowHeight(30);
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setPreferredWidth(175
            );
        }
        this.addStatePublisher = addStatePublisher;
        this.removeStatePublisher = removeStatePublisher;
        this.stateRegister = stateRegister;
        this.configuration = configuration;

        this.stateRegister.getAddStatePublisher().subscribe(this::onStateAdd);
        this.stateRegister.getRemoveStatePublisher().subscribe(this::onStateRemove);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }
            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = rowAtPoint(e.getPoint());
                    int col = columnAtPoint(e.getPoint());
                    setRowSelectionInterval(row, row);
                    setColumnSelectionInterval(col, col);
                    Object cellValue = StateRegisterUI.this.getValueAt(row, col);
                    StateRegisterUI.this.statePopupMenu = new StateRegisterPopupMenu(receiver, cellValue);
                    statePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void onStateAdd(observer.events.AddStateEvent event) {
        // Add UI State
    }
    public void onStateRemove(observer.events.RemoveStateEvent event) {
        // Remove UI State
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column){
        return new StateRegisterRenderer(this.receiver, this.stateRegister, this.configuration);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (column == 0) {
            return super.getCellEditor(row, column);
        }
        return new TransitionEditor(this.receiver, this.stateRegister, this.configuration);
    }
}
