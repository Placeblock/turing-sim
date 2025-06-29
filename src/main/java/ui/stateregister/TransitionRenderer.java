package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@AllArgsConstructor
public class TransitionRenderer implements TableCellRenderer {
    private final StateRegister stateRegister;
    private final Configuration configuration;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {


        //Render Transition
        if (o instanceof Transition) {
            Transition transition = (Transition) o;
            State newState = transition.getNewState();

            return new TransitionRenderTextField(stateRegister, configuration.getTapeAlphabet(), transition);
        // Render Column and Row Name
        } else {
            return new JLabel(String.valueOf(o));
        }
    }
}
