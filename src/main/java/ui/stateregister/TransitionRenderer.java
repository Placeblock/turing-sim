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
        Transition transition = (Transition) o;

        return new TransitionPanel(SampleStateRegister.get(), SampleTransitionAlphabet.get(), transition);
    }
}
