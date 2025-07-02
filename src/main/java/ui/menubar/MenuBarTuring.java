package ui.menubar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
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

    public MenuBarTuring(Receiver receiver) {
        super();
        // TODO logic on the machine side needs to be implemented
        this.saveTapeEmitter = new Emitter<>(receiver);
        this.saveTransitionsEmitter = new Emitter<>(receiver);

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveTapeItem = new JMenuItem("Save Tape");
        JMenuItem saveTransitionsItem = new JMenuItem("Save Transitions");

        saveTapeItem.addActionListener(this::saveTapeActionListener);
        saveTransitionsItem.addActionListener(this::saveTransitionsActionListener);

        fileMenu.add(saveTapeItem);
        fileMenu.add(saveTransitionsItem);

        add(fileMenu);
    }

    private void saveTapeActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("tape.txt")); // Default file name

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveTapeEmitter.emit(new SaveTapeEvent(selectedFile.getAbsolutePath()));
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); //!TEST
        }
    }

    private void saveTransitionsActionListener(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("transitions.txt")); // Default file name

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveTransitionsEmitter.emit(new SaveTransitionsEvent(selectedFile.getAbsolutePath()));
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); //!TEST
        }
    }
}