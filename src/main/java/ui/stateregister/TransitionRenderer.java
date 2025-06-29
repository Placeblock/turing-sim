package ui.stateregister;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class TransitionRenderer implements TableCellRenderer {
    private final StateRegister stateRegister;
    private final Configuration configuration;

    public TransitionRenderer(StateRegister stateRegister, Configuration configuration) {
        this.stateRegister = stateRegister;
        this.configuration = configuration;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, int row, int column) {


        //Render Transition
        if (o instanceof Transition) {
            Transition transition = (Transition) o;
            State newState = transition.getNewState();
            //Error here: does not find the new State (returns -1)
//            System.out.println( "newState:" + stateRegister.getStates().indexOf(newState));
//            System.out.println("newSymbol" + transition.getNewSymbol());
//            System.out.println("move:" + transition.getMove());


            return new TransitionRenderTextField(stateRegister, configuration.getTapeAlphabet(), transition);
        // Render Column and Row Name
        } else {
            return new JLabel(String.valueOf(o));
        }
    }
}
