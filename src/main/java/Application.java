import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

import ui.StartupWindow;

public class Application {

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatIntelliJLaf()); }
        catch (Exception e) { e.printStackTrace(); }

        javax.swing.SwingUtilities.invokeLater(() -> {
            var startupWindow = new StartupWindow();
            startupWindow.setVisible(true);
        });
    }
}
