package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@AllArgsConstructor
public class TransitionRenderer implements TableCellRenderer {
    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        if (row > 0 && column > 0) {
            Transition transition = (Transition) o;
            return new TransitionPanel(receiver, stateRegister, configuration, transition);
        }
        return new JLabel(String.valueOf(o));
    }
}
