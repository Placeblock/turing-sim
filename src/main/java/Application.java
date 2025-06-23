import core.State;
import ui.MainWindow;
import ui.StateUI;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            List<State> stateRegister = new ArrayList<>();
            stateRegister.add(new State(null, false));
            mainWindow.getContentPane().add(new StateUI(null, null, stateRegister));
        });
    }
}
