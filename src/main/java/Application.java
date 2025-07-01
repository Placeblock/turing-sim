import controller.StateRegisterController;
import core.Configuration;
import core.StateRegister;
import event.Receiver;
import ui.MainWindow;
import ui.stateregister.StateRegisterUI;
import util.SampleStateRegister;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            StateRegister stateRegister = SampleStateRegister.get();
            StateRegisterController stateRegisterController = new StateRegisterController(stateRegister);
            Receiver receiver = stateRegisterController.getReceiver();

            Configuration configuration = new Configuration();
            StateRegisterUI stateRegisterUI = new StateRegisterUI(receiver, stateRegister, configuration, null, null);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }
}
