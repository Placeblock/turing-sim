package ui;

import com.formdev.flatlaf.FlatIntelliJLaf;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainWindow extends JFrame {
    public MainWindow(Configuration config, StateRegister stateRegister) {
        setTitle("Turing Maschine");
        setSize(1000, 600);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Controllers and Machine Setup ===
        ConfigurationController configurationController = new ConfigurationController(config, stateRegister);
        Receiver receiver = configurationController.getReceiver();
        Tape<Character> tape = new Tape<>(config.getBlankSymbol(), config.getInitialTapeState());
        MachineState machineState = new MachineState(tape, config.getInitialState());
        Machine machine = new Machine(machineState);
        MachineController machineController = new MachineController(machine);
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // === Menu Bar ===
        setJMenuBar(new MenuBarTuring(receiver));

        // === Layout Manager ===
        Container content = getContentPane();
        content.setLayout(new BorderLayout(10, 10));

        // === Top Panel (File Open Button) ===
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton openButton = new JButton("Open File");
        openButton.addActionListener(e -> openFileChooser());
        topPanel.add(openButton);
        content.add(topPanel, BorderLayout.NORTH);

        // === Center Panel (Tape + Controls) ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tape.moveNext(); tape.moveNext(); tape.moveNext();
        TapeUI tapeUI = new TapeUI(tape);
        TuringMachineControlsUI controlsUI = new TuringMachineControlsUI(machineController.getReceiver());

        centerPanel.add(tapeUI);
        centerPanel.add(Box.createVerticalStrut(10)); // spacing
        centerPanel.add(controlsUI);

        content.add(centerPanel, BorderLayout.CENTER);

        // === Right Panel (State Register UI) ===
        StateRegisterUI stateRegisterUI = new StateRegisterUI(receiver, stateRegister, config);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("State Register"));
        rightPanel.add(stateRegisterUI, BorderLayout.CENTER);

        // Set initial preferred size
        int initialWidth = (int) (getWidth() * 0.25); // 25% of window width
        rightPanel.setPreferredSize(new Dimension(initialWidth, getHeight()));

        content.add(rightPanel, BorderLayout.EAST);

        // === Dynamically resize right panel ===
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = (int) (getWidth() * 0.5);
                rightPanel.setPreferredSize(new Dimension(width, getHeight()));
                content.revalidate();
            }
        });

        // === Click Anywhere to Stop Editing ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stateRegisterUI.stopEditing();
            }
        });
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