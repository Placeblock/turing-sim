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
import ui.configuration.InitialStateUI;
import ui.menubar.MenuBarTuring;
import ui.player.StatusPanel;
import ui.player.TuringMachineControlsUI;
import ui.stateregister.StateRegisterUI;
import ui.tape.TapeUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main application window for the Turing machine simulator.
 * 
 * <p>This class creates and manages the primary user interface, including the
 * tape visualization, state register table, control panels, and menu system.
 * It coordinates between various UI components and establishes the overall
 * layout and user interaction patterns.</p>
 * 
 * <p>The window integrates multiple controllers and UI components to provide
 * a cohesive user experience for designing, configuring, and executing
 * Turing machines.</p>
 * 
 * @see Configuration
 * @see StateRegister
 * @see Machine
 */
public class MainWindow extends JFrame {
    public MainWindow(Configuration config, StateRegister stateRegister) {
        this(config, stateRegister, null);
    }

    public MainWindow(Configuration config, StateRegister stateRegister, StartupWindow startupWindow) {
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
        MachineController machineController = new MachineController(machine, config);
        try {
            System.setProperty("flatlaf.uiScale", "1.2");
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // === Menu Bar ===
        setJMenuBar(new MenuBarTuring(this, receiver, startupWindow));

        // === Layout Manager ===
        Container content = getContentPane();
        content.setLayout(new BorderLayout(10, 10));

        // === Top Panel (File Open Button) ===
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new InitialStateUI(config, receiver));
        StatusPanel statusPanel = new StatusPanel(machineState.getCurrentStateChangedPublisher(), stateRegister);
        topPanel.add(statusPanel);
        content.add(topPanel, BorderLayout.NORTH);

        // === Center Panel (Tape + Controls) ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        TapeUI tapeUI = new TapeUI(tape);
        TuringMachineControlsUI controlsUI = new TuringMachineControlsUI(machineController.getReceiver());

        centerPanel.add(tapeUI);
        centerPanel.add(Box.createVerticalStrut(10)); // spacing
        centerPanel.add(controlsUI);

        content.add(centerPanel, BorderLayout.CENTER);

        // === Right Panel (State Register UI) ===
        StateRegisterUI stateRegisterUI = new StateRegisterUI(receiver, stateRegister, config);
        JPanel rightPanel = new JPanel(new BorderLayout());
        JScrollPane tableScrollPane = new JScrollPane(
                stateRegisterUI,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.setBorder(BorderFactory.createTitledBorder("State Register"));

        // Set initial preferred size
        int initialWidth = (int) (getWidth() * 0.25);
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
}