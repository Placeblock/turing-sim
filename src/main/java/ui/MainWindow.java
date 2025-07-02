package ui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ConfigurationController;
import controller.MachineController;
import core.Configuration;
import core.Machine;
import core.MachineState;
import core.StateRegister;
import core.tape.Tape;
import event.Receiver;
import ui.menubar.MenuBarTuring;
import ui.player.TuringMachineControlsUI;
import ui.stateregister.StateRegisterUI;
import ui.tape.TapeUI;

import java.awt.FlowLayout;

public class MainWindow extends JFrame {
    public final int a = 1;
    public MainWindow(Configuration config, StateRegister stateRegister) {
        setTitle("Turing Maschine");
        setSize(800, 450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        ConfigurationController configurationController = new ConfigurationController(config, stateRegister);
        Receiver receiver = configurationController.getReceiver();
        Tape<Character> tape = new Tape<>(config.getBlankSymbol(), config.getInitialTapeState());
        MachineState machineState = new MachineState(tape, config.getInitialState());
        Machine machine = new Machine(machineState);
        MachineController machineController = new MachineController(machine);

        // Menu bar setup
        MenuBarTuring menuBar = new MenuBarTuring(new Receiver());
        setJMenuBar(menuBar);

        JButton openButton = new JButton("Open File");
        openButton.addActionListener(e -> openFileChooser());

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(openButton);

        tape.moveNext();
        tape.moveNext();
        tape.moveNext();

        TapeUI tapePanel = new TapeUI(tape);
        getContentPane().add(tapePanel);

        //! TEST
        JButton testCharacterChangedEventButton = new JButton("Test Character Changed Event");
        testCharacterChangedEventButton.addActionListener(e -> {
            tapePanel.aaa.setSymbol('T');
        });
        getContentPane().add(testCharacterChangedEventButton);

        //! TEST
        JButton testHeadChangedEventButton = new JButton("Test Head Changed Event");
        testHeadChangedEventButton.addActionListener(e -> {
            tape.moveNext();
        });
        getContentPane().add(testHeadChangedEventButton);

        add(new TuringMachineControlsUI(machineController.getReceiver()));

        StateRegisterUI stateRegisterUI = new StateRegisterUI(receiver, stateRegister, config);
        this.getContentPane().add(stateRegisterUI);
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected file: " + selectedFile.getAbsolutePath());
        }
    }
}
