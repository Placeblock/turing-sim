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
    private final Emitter<AddStateEvent> addStateEmitter;
    private final Emitter<RemoveStateEvent> removeStateEmitter;

    private final Receiver stateRegisterReceiver;
    private final Receiver configurationReceiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;
    private final StateRegisterTableModel tableModel;

    private JPopupMenu statePopupMenu;
    public StateRegisterUI(Receiver stateRegisterReceiver, Receiver configurationReceiver,
                           StateRegister stateRegister, Configuration configuration,
                           Emitter<AddStateEvent> addStateEmitter,
                           Emitter<RemoveStateEvent> removeStateEmitter){
        this(stateRegisterReceiver, configurationReceiver, stateRegister, configuration,
             new StateRegisterTableModel(stateRegister, configuration), addStateEmitter, removeStateEmitter);
    }


    private StateRegisterUI(Receiver stateRegisterReceiver, Receiver configurationReceiver,
                           StateRegister stateRegister, Configuration configuration,
                           StateRegisterTableModel tableModel,
                           Emitter<AddStateEvent> addStateEmitter,
                           Emitter<RemoveStateEvent> removeStateEmitter) {
        super(tableModel);
        this.stateRegisterReceiver = stateRegisterReceiver;
        this.configurationReceiver = configurationReceiver;
        this.tableModel = tableModel;

        this.setRowHeight(30);
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setPreferredWidth(175
            );
        }
        this.addStateEmitter = addStateEmitter;
        this.removeStateEmitter = removeStateEmitter;
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
                    StateRegisterUI.this.statePopupMenu = new StateRegisterPopupMenu(stateRegisterReceiver, configurationReceiver, cellValue);
                    statePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void onStateAdd(observer.events.AddStateEvent event) {
        // Add UI State
        tableModel.fireTableDataChanged();
    }
    public void onStateRemove(observer.events.RemoveStateEvent event) {
        // Remove UI State
        tableModel.fireTableDataChanged();
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column){
        return new StateRegisterRenderer(this.stateRegisterReceiver, this.stateRegister, this.configuration);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (column == 0) {
            return super.getCellEditor(row, column);
        }
        return new TransitionEditor(this.stateRegisterReceiver, this.stateRegister, this.configuration);
    }

}
