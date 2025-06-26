package ui.tape;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import core.tape.Tape;
import core.tape.TapeCell;
import observer.events.TapeHeadPositionChangedEvent;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

public class TapeUI extends JPanel {

    public TapeCell<Character> aaa;

    private Map<TapeCell<Character>, TapeCellUI> cellToUIMap = new HashMap<>();
    private TapeCellUI headCellUI;
    
    public TapeUI(Tape<Character> tape) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        var cells = tape.getAllCells();
        var head = tape.getHead();
        
        JPanel tapePanel = new JPanel(new GridBagLayout());
        add(tapePanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); 
        
        int gridx = 0;

        for (TapeCell<Character> cell : cells) {
            var cellUI = new TapeCellUI(cell);
            gbc.gridx = gridx++;
            tapePanel.add(cellUI, gbc);
            this.cellToUIMap.put(cell, cellUI);

            if (cell.equals(head)) {
                cellUI.setBorder(new LineBorder(Color.RED, 2)); // Highlight head cell
                this.aaa = cell; // Store the head cell reference
                headCellUI = cellUI; // Store the head cell UI reference
            }
        }

        // Subscribe to head position changes
        tape.getHeadPositionChangedPublisher().subscribe(this::onHeadPositionChanged);
    }

    private void onHeadPositionChanged(TapeHeadPositionChangedEvent<Character> event) {
        this.headCellUI.setBorder(new LineBorder(Color.BLACK)); // Reset previous head cell border
        this.headCellUI = this.cellToUIMap.get(event.newHeadPosition());
        this.headCellUI.setBorder(new LineBorder(Color.RED, 2)); // Highlight new head cell
    }
}
