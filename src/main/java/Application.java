import core.Move;
import core.State;
import core.StateRegister;
import core.Transition;
import ui.MainWindow;
import ui.stateregister.StateRegisterUI;
import util.SampleStateRegister;
import util.SampleTransitionAlphabet;

import java.util.*;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            StateRegister stateRegister = SampleStateRegister.get();
            StateRegisterUI stateRegisterUI = new StateRegisterUI(stateRegister, null, null);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }
}
