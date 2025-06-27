package ui.stateregister;

import core.State;
import core.StateRegister;
import core.Transition;
import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Set;

@AllArgsConstructor
public class TransitionRenderer implements TableCellRenderer {
    private final StateRegister stateRegister;
    private final Set<Character> tapeAlphabet;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        //Render Transition
        if (o instanceof Transition) {
            Transition transition = (Transition) o;
            System.out.println(transition);
            State newState = transition.getNewState();
            //Error here: does not find the new State (returns -1)
            System.out.println( "newState:" + stateRegister.getStates().indexOf(newState));
            System.out.println("newSymbol" + transition.getNewSymbol());
            System.out.println("move:" + transition.getMove());


            return new TransitionPanel(stateRegister, tapeAlphabet, transition);
        // Render Column and Row Name
        } else {
            return new JLabel(String.valueOf(o));
        }
    }
}
