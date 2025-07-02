import controller.ConfigurationController;
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
            Configuration configuration = new Configuration();
            ConfigurationController configurationController = new ConfigurationController(configuration, stateRegister);
            Receiver receiver = configurationController.getReceiver();

            StateRegisterUI stateRegisterUI = new StateRegisterUI(receiver, stateRegister, configuration);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }
}
