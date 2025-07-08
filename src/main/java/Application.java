import core.Configuration;
import core.StateRegister;
import ui.MainWindow;
import util.SampleStateRegister;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            StateRegister stateRegister = SampleStateRegister.get();
            Configuration config = new Configuration(stateRegister.getStates().getFirst());

            config.setInitialTapeState("000001010101");

            MainWindow mainWindow = new MainWindow(config, stateRegister);
            mainWindow.setVisible(true);
        });
    }
}
