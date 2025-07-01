package ui.player;

import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class TuringMachineControlsUI extends JPanel {
    
    public TuringMachineControlsUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        
        JLabel speedLabel = new JLabel("Speed:");
        JSlider speedSlider = new JSlider(1, 10, 2);
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
        
        topPanel.add(speedLabel);
        topPanel.add(speedSlider);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        
        JButton stepButton = new JButton("Step");
        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");
        
        bottomPanel.add(stepButton);
        bottomPanel.add(playButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(resetButton);
        
        add(topPanel);
        add(bottomPanel);
    }
}
