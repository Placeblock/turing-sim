import controller.ConfigurationController;
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
            Configuration configuration = new Configuration();
            StateRegisterController stateRegisterController = new StateRegisterController(stateRegister);
            ConfigurationController configurationController = new ConfigurationController(configuration);
            Receiver stateRegisterReceiver = stateRegisterController.getReceiver();
            Receiver configurationReceiver = configurationController.getReceiver();

            StateRegisterUI stateRegisterUI = new StateRegisterUI(stateRegisterReceiver, configurationReceiver, stateRegister, configuration);
            mainWindow.getContentPane().add(stateRegisterUI);
        });
    }
}
