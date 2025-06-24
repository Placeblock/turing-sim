import core.Move;
import core.State;
import core.StateRegister;
import core.Transition;
import ui.MainWindow;
import ui.stateregister.StateRegisterUI;

import java.util.*;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            StateRegister stateRegister = createStateRegister();
            StateRegisterUI stateRegisterUI = new StateRegisterUI(stateRegister, null, null);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }

    private static final int TEST_STATES = 3;

    public static StateRegister createStateRegister() {
        StateRegister stateRegister = new StateRegister();
        Set<Character> symbols = Set.of('A', 'B', 'C');

        for (int i = 0; i < TEST_STATES; i++) {
            Map<Character, Transition> transitions = new HashMap<>();
            for (Character symbol : symbols) {
                transitions.put(symbol, new Transition(symbol, Move.LEFT, null));
            }
            stateRegister.addState(i, new State(transitions, i==TEST_STATES-1));
        }

        for (int i = 0; i < TEST_STATES; i++) {
            State state = stateRegister.getStates().get(i);
            for (Transition transition : state.getTransitions().values()) {
                State newState = stateRegister.getStates().get((i + 1) % TEST_STATES);
                transition.setNewState(newState);
            }
        }
        
        return stateRegister;
    }
}
