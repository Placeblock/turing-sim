import core.Move;
import core.State;
import core.StateRegister;
import core.Transition;
import ui.MainWindow;
import ui.stateregister.StateRegisterUI;

import java.util.*;

public class Application {

    public static final String[][] DATA = {
            {" 1/1987", "195", "Vergleichstest EGA-Karten"},
            {" 2/1987", "171", "Schneider PC: BewÃ¤hrungsprobe"},
            {" 3/1987", "235", "Luxus-Textsyteme im Vergleich"},
            {" 4/1987", "195", "Turbo BASIC"},
            {" 5/1987", "211", "640-K-Grenze durchbrochen"},
            {" 6/1987", "211", "Expertensysteme"},
            {" 7/1987", "199", "IBM Model 30 im Detail"},
            {" 8/1987", "211", "PAK-68: Tuning fÃ¼r 68000er"},
            {" 9/1987", "215", "Desktop Publishing"},
            {"10/1987", "279", "2,5 MByte im ST"},
            {"11/1987", "279", "Transputer-Praxis"},
            {"12/1987", "271", "Preiswert mit 24 Nadeln"},
            {" 1/1988", "247", "Schnelle 386er"},
            {" 2/1988", "231", "Hayes-kompatible Modems"},
            {" 3/1988", "295", "TOS/GEM auf 68020"},
            {" 4/1988", "263", "Projekt Super-EGA"},
            {" 5/1988", "263", "Neuheiten auf der CeBIT 88"},
            {" 6/1988", "231", "9600-Baud-Modem am Postnetz"}
    };

    public static final String[] COLHEADS = {
            "Ausgabe", "Seiten", "Titelthema"
    };

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
