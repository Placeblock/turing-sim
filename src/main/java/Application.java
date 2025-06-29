import core.Configuration;
import core.StateRegister;
import ui.MainWindow;
import ui.stateregister.StateRegisterUI;
import util.SampleStateRegister;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            StateRegister stateRegister = SampleStateRegister.get();
            Configuration configuration = new Configuration();
            StateRegisterUI stateRegisterUI = new StateRegisterUI(stateRegister, configuration, null, null);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }
}
