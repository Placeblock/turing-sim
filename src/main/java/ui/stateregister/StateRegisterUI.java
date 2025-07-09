package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import event.Receiver;
import observer.events.TransitionCreatedEvent;
import observer.events.TransitionRemovedEvent;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class StateRegisterUI extends JTable {
    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;
    private final StateRegisterTableModel tableModel;

    private JPopupMenu statePopupMenu;
    public StateRegisterUI(Receiver receiver,
                           StateRegister stateRegister, Configuration configuration){
        this(receiver, stateRegister, configuration,
             new StateRegisterTableModel(stateRegister, configuration));
    }


    private StateRegisterUI(Receiver receiver,
                           StateRegister stateRegister, Configuration configuration,
                           StateRegisterTableModel tableModel) {
        super(tableModel);
        this.receiver = receiver;
        this.tableModel = tableModel;
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setTableHeader(null);

        this.setRowHeight(35);
        updateColumnWidth();
        this.stateRegister = stateRegister;
        this.configuration = configuration;

        this.stateRegister.getAddStatePublisher().subscribe(this::onStateAdd);
        this.stateRegister.getRemoveStatePublisher().subscribe(this::onStateRemove);
        this.configuration.getTapeSymbolsChangedPublisher().subscribe(this::onTapeSymbolChanged);
        this.configuration.getInitialStateChangedPublisher().subscribe(this::onInitialStateChange);
        this.configuration.getBlankSymbolChangedPublisher().subscribe(this::onBlankSymbolChange);

        for (State state : this.stateRegister.getStates()) {
            state.getTransitionCreatedPublisher().subscribe(this::onTransitionCreate);
            state.getTransitionRemovedPublisher().subscribe(this::onTransitionRemove);
        }

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
                    // Stop any ongoing cell editing before showing popup
                    StateRegisterUI.this.stopEditing();
                    
                    int row = rowAtPoint(e.getPoint());
                    int col = columnAtPoint(e.getPoint());
                    setRowSelectionInterval(row, row);
                    setColumnSelectionInterval(col, col);
                    Object cellValue = StateRegisterUI.this.getValueAt(row, col);
                    StateRegisterUI.this.statePopupMenu = new StateRegisterPopupMenu(receiver, stateRegister, cellValue, row, StateRegisterUI.this);
                    statePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void stopEditing() {
        if (this.isEditing()) {
            this.getCellEditor().stopCellEditing();
        }
    }

    private void updateColumnWidth() {
        for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
            if (i == 0) {
                this.getColumnModel().getColumn(i).setPreferredWidth(50);
            } else {
                this.getColumnModel().getColumn(i).setPreferredWidth(250);
            }
        }
    }

    public void onStateAdd(observer.events.AddStateEvent event) {
        tableModel.fireTableRowsInserted(0, event.index());
        tableModel.fireTableDataChanged();
        event.state().getTransitionCreatedPublisher().subscribe(this::onTransitionCreate);
        event.state().getTransitionRemovedPublisher().subscribe(this::onTransitionRemove);
    }
    public void onStateRemove(observer.events.RemoveStateEvent event) {
        tableModel.fireTableRowsDeleted(0, tableModel.getRowCount());
        tableModel.fireTableDataChanged();
        event.state().getTransitionCreatedPublisher().unsubscribe(this::onTransitionCreate);
        event.state().getTransitionRemovedPublisher().subscribe(this::onTransitionRemove);
    }
    public void onTransitionCreate(TransitionCreatedEvent event) {
        tableModel.fireTableDataChanged();
    }
    public void onTransitionRemove(TransitionRemovedEvent event) {
        System.out.println("TABLE DATA CHANGED");
        tableModel.fireTableDataChanged();
    }

    public void onTapeSymbolChanged(observer.events.TapeSymbolsChangedEvent event) {
        System.out.println("TAPE SYMBOL CHANGED");
        tableModel.fireTableStructureChanged();
        this.updateColumnWidth();
    }
    public void onInitialStateChange(observer.events.InitialStateChangedEvent event) {
        tableModel.fireTableDataChanged();
    }
    public void onBlankSymbolChange(observer.events.BlankSymbolChangedEvent event) {
        tableModel.fireTableDataChanged();
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column){
        return new StateRegisterRenderer(this.stateRegister, this.configuration);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (column == 0) {
            return super.getCellEditor(row, column);
        }
        if (row == 0) return null;
        State state = this.stateRegister.getStates().get(row - 1);
        
        // Prevent editing if state is terminating
        if (state.isTerminates()) {
            return null;
        }
        
        Character symbol = this.configuration.getTapeSymbol(column - 1);
        return new TransitionEditor(this.receiver, this.stateRegister, this.configuration, state, symbol);
    }



}
