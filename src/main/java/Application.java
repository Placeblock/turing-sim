import ui.StartupWindow;

public class Application {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            var startupWindow = new StartupWindow();
            startupWindow.setVisible(true);
        });
    }
}
