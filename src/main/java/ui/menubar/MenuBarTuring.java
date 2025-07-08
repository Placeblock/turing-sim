package ui.menubar;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import event.Emitter;
import event.Receiver;
import event.events.SaveTapeEvent;
import event.events.SaveTransitionsEvent;

public class MenuBarTuring extends JMenuBar {

    private final Emitter<SaveTapeEvent> saveTapeEmitter;
    private final Emitter<SaveTransitionsEvent> saveTransitionsEmitter;

    private final JFrame parentFrame;
    private final JFrame startupWindow;

    public MenuBarTuring(JFrame parentFrame, Receiver receiver, JFrame startupWindow) {
        super();
        this.saveTapeEmitter = new Emitter<>(receiver);
        this.saveTransitionsEmitter = new Emitter<>(receiver);
        this.parentFrame = parentFrame;
        this.startupWindow = startupWindow;

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveTransitionsItem = new JMenuItem("Save Configuration");
        JMenuItem saveTapeItem = new JMenuItem("Save initial Tape");
        JMenuItem quitToMenuItem = new JMenuItem("Quit to Menu");
        JMenuItem exitItem = new JMenuItem("Exit");

        saveTransitionsItem.addActionListener(this::saveTransitionsActionListener);
        saveTapeItem.addActionListener(this::saveTapeActionListener);
        quitToMenuItem.addActionListener(this::quitToMenuActionListener);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(saveTransitionsItem);
        fileMenu.add(saveTapeItem);
        fileMenu.addSeparator();
        if(startupWindow != null) fileMenu.add(quitToMenuItem);
        fileMenu.add(exitItem);

        add(fileMenu);
    }

    private void quitToMenuActionListener(ActionEvent e) {
        parentFrame.dispose();
        startupWindow.setVisible(true);
    }

    private void saveTransitionsActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("config.txt")); // Default file name

        int returnValue = fileChooser.showSaveDialog(parentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveTransitionsEmitter.emit(new SaveTransitionsEvent(selectedFile));
        }
    }

    private void saveTapeActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("tape.txt")); // Default file name

        int returnValue = fileChooser.showSaveDialog(parentFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveTapeEmitter.emit(new SaveTapeEvent(selectedFile));
        }
    }
}