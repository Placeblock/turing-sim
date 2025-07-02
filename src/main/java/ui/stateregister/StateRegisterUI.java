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
    private final Receiver stateRegisterReceiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;
    private final StateRegisterTableModel tableModel;

    private JPopupMenu statePopupMenu;
    public StateRegisterUI(Receiver stateRegisterReceiver, Receiver configurationReceiver,
                           StateRegister stateRegister, Configuration configuration){
        this(stateRegisterReceiver, configurationReceiver, stateRegister, configuration,
             new StateRegisterTableModel(stateRegister, configuration));
    }


    private StateRegisterUI(Receiver stateRegisterReceiver, Receiver configurationReceiver,
                           StateRegister stateRegister, Configuration configuration,
                           StateRegisterTableModel tableModel) {
        super(tableModel);
        this.stateRegisterReceiver = stateRegisterReceiver;
        this.tableModel = tableModel;

        this.setRowHeight(30);
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setPreferredWidth(175);
        }
        this.stateRegister = stateRegister;
        this.configuration = configuration;

        this.stateRegister.getAddStatePublisher().subscribe(this::onStateAdd);
        this.stateRegister.getRemoveStatePublisher().subscribe(this::onStateRemove);
        this.configuration.getTapeSymbolsChangedPublisher().subscribe(this::onTapeSymbolChanged);

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

    public void onTapeSymbolChanged(observer.events.TapeSymbolsChangedEvent event) {
        System.out.println("TAPE SYMBOL CHANGED");
        // Remove UI Symbol
        tableModel.fireTableStructureChanged();
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
