package ui.stateregister;

import core.Transition;
import util.SampleStateRegister;
import util.SampleTransitionAlphabet;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TransitionRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        //Render Transition
        if (o instanceof Transition) {
            Transition transition = (Transition) o;
            System.out.println(transition.getMove().toString() + transition.getNewSymbol() + transition.getNewState().toString());


            return new TransitionPanel(SampleStateRegister.get(), SampleTransitionAlphabet.get(), transition);
        // Render Column and Row Name
        } else {
            return new JLabel(String.valueOf(o));
        }
    }
}
