import ui.MainWindow;

public class Application {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }
}
