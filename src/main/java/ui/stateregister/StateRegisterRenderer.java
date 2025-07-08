package ui.stateregister;

import core.State;
import core.StateRegister;
import core.Transition;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class StateRegisterRenderer extends JPanel implements TableCellRenderer {
    private final StateRegister stateRegister;
    public StateRegisterRenderer(StateRegister stateRegister) {
        this.stateRegister = stateRegister;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setBorder(new LineBorder(Color.lightGray, 1));
        if (row > 0 && column > 0) {
            Transition transition = (Transition) o;
            if (transition == null) {
                this.add(new JLabel("-", SwingConstants.CENTER));
            } else {
                int stateIndex = stateRegister.getStates().indexOf(transition.getNewState());
                
                String text = String.format("( %s, %s, %s )",
                    stateIndex == -1 ? " " : "q"+stateIndex,
                    transition.getNewSymbol() == null ? " " : transition.getNewSymbol(),
                    transition.getMove().getSymbol());

                JLabel label = new JLabel(text, SwingConstants.CENTER);

                if (transition.getNewState() == null || transition.getNewSymbol() == null) {
                    this.setBackground(new Color(0xFFADB0));
                }

                this.add(label);
            }
        }
        if(row > 0 && column == 0) {
            State state = (State) o;
            JLabel label = new JLabel("q" + stateRegister.getStates().indexOf(state), SwingConstants.CENTER);
            if(state.isTerminates()) label.setForeground(Color.RED);
            this.add(label);
        }
        if(row == 0 && column != 0){
            JLabel label = new JLabel(String.valueOf(o));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(label);
        }
        return this;
    }

}
