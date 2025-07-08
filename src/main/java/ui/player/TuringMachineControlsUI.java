package ui.player;

import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import event.Emitter;
import event.Receiver;
import event.events.*;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class TuringMachineControlsUI extends JPanel {

    private static final int DEFAULT_SPEED_MS = 250;

    private final Emitter<StepEvent> stepEmitter;
    private final Emitter<ResetEvent> resetEmitter;
    private final Emitter<StartEvent> startEmitter;
    private final Emitter<StopEvent> stopEmitter;
    private final Emitter<IntervalEvent> intervalEmitter;
    private final JSlider speedSlider;

    public TuringMachineControlsUI(Receiver receiver) {
        this.stepEmitter = new Emitter<>(receiver);
        this.resetEmitter = new Emitter<>(receiver);
        this.startEmitter = new Emitter<>(receiver);
        this.stopEmitter = new Emitter<>(receiver);
        this.intervalEmitter = new Emitter<>(receiver);
        // To step use this.stepEmitter.emit(new StepEvent());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        
        JLabel speedLabel = new JLabel("Speed:");
        speedSlider = new JSlider(1, 10, 2);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setSnapToTicks(true);
        
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        //labelTable.put(1, new JLabel("0.5x"));
        labelTable.put(2, new JLabel("1x"));
        labelTable.put(4, new JLabel("2x"));
        labelTable.put(6, new JLabel("3x"));
        labelTable.put(8, new JLabel("4x"));
        labelTable.put(10, new JLabel("5x"));
        speedSlider.setLabelTable(labelTable);
        
        speedSlider.addChangeListener(this::onSpeedSliderChanged);
        
        topPanel.add(speedLabel);
        topPanel.add(speedSlider);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        
        JButton stepButton = new JButton("Step");
        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");
        
        stepButton.addActionListener(this::onStepButtonClicked);
        playButton.addActionListener(this::onPlayButtonClicked);
        stopButton.addActionListener(this::onStopButtonClicked);
        resetButton.addActionListener(this::onResetButtonClicked);
        
        bottomPanel.add(stepButton);
        bottomPanel.add(playButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(resetButton);
        
        add(topPanel);
        add(bottomPanel);

        this.intervalEmitter.emit(new IntervalEvent(DEFAULT_SPEED_MS));
    }

    private void onSpeedSliderChanged(ChangeEvent e) {
        this.intervalEmitter.emit(new IntervalEvent(this.getDelayFromSlider()));
    }
    
    private int getDelayFromSlider() {
        int sliderValue = speedSlider.getValue();
        double multiplier = sliderValue / 2.0; // Convert slider value to multiplier
        return (int) (DEFAULT_SPEED_MS / multiplier); // base delay divided by multiplier
    }
    
    private void onStepButtonClicked(ActionEvent e) {
        this.stepEmitter.emit(new StepEvent());
    }
    
    private void onPlayButtonClicked(ActionEvent e) {
        this.startEmitter.emit(new StartEvent());
    }
    
    private void onStopButtonClicked(ActionEvent e) {
        this.stopEmitter.emit(new StopEvent());
    }
    
    private void onResetButtonClicked(ActionEvent e) {
        this.resetEmitter.emit(new ResetEvent());
    }
}
