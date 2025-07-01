package ui.player;

import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import event.Emitter;
import event.Receiver;
import event.events.StepEvent;

import java.awt.event.ActionEvent;

public class TuringMachineControlsUI extends JPanel {

    private final Emitter<StepEvent> stepEmitter;
    private Timer playTimer;
    private JSlider speedSlider;
    private boolean isPlaying = false;
    
    public TuringMachineControlsUI(Receiver receiver) {
        this.stepEmitter = new Emitter<>(receiver);
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
    }
    
    private void onSpeedSliderChanged(javax.swing.event.ChangeEvent e) {
        if (isPlaying && playTimer != null) {
            int newDelay = getDelayFromSlider();
            playTimer.setDelay(newDelay);
        }
    }
    
    private int getDelayFromSlider() {
        int sliderValue = speedSlider.getValue();
        double multiplier = sliderValue / 2.0; // Convert slider value to multiplier
        return (int) (500 / multiplier); // 500ms base delay divided by multiplier
    }
    
    private void onStepButtonClicked(ActionEvent e) {
        this.stepEmitter.emit(new StepEvent());
    }
    
    private void onPlayButtonClicked(ActionEvent e) {
        if (!isPlaying) {
            isPlaying = true;
            int delay = getDelayFromSlider();
            playTimer = new Timer(delay, (ActionEvent ae) -> {
                this.stepEmitter.emit(new StepEvent());
            });
            playTimer.start();
        }
    }
    
    private void onStopButtonClicked(ActionEvent e) {
        if (isPlaying && playTimer != null) {
            playTimer.stop();
            isPlaying = false;
        }
    }
    
    private void onResetButtonClicked(ActionEvent e) {
        // Stop the timer if playing
        if (isPlaying && playTimer != null) {
            playTimer.stop();
            isPlaying = false;
        }
        // TODO: Implement reset functionality
    }
}
