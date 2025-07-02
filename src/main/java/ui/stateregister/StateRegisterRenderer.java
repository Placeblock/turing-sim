package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@RequiredArgsConstructor
public class StateRegisterRenderer implements TableCellRenderer {
    private final Receiver receiver;
    private final StateRegister stateRegister;
    private final Configuration configuration;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        if (row > 0 && column > 0) {
            Transition transition = (Transition) o;
            if (transition == null) return new JLabel("-");
            int stateIndex = stateRegister.getStates().indexOf(transition.getNewState());
            String text = String.format("(q%d, %s, %s)", stateIndex, transition.getNewSymbol(), transition.getMove().getSymbol());
            return new JLabel(text);
        }
        if(row > 0 && column == 0) {
            // Render state name
            State state = (State) o;
            return new JLabel("q" + stateRegister.getStates().indexOf(state));
        }
        if(row == 0 && column != 0){
            return new JLabel(String.valueOf(o));
        }
        return null;
    }
}
