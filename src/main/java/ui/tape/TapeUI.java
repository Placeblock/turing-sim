package ui.tape;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import core.tape.TapeCell;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class TapeUI extends JPanel {

    private List<TapeCellUI> tapeCellsLeft; // List to hold tape cells
    private TapeCellUI headCell; // Head cell
    private List<TapeCellUI> tapeCellsRight; // List to hold tape cells

    public TapeCell<Character> aaa;
    
    public TapeUI(int cellAmountLeftOfHead, int cellAmountRightOfHead) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.tapeCellsLeft = new ArrayList<>();
        this.tapeCellsRight = new ArrayList<>();
        
        JPanel tapePanel = new JPanel(new GridBagLayout());
        add(tapePanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); // 5px space to the right of each cell

        int gridx = 0;
        for (int i = 0; i < cellAmountLeftOfHead; i++) {
            var cell = new TapeCellUI("");
            gbc.gridx = gridx++;
            tapePanel.add(cell, gbc); // Placeholder for left cells
            this.tapeCellsLeft.add(cell); // Add to left cells list
        }

        // Head cell
        aaa = new TapeCell<>('X');
        headCell = new TapeCellUI(aaa);
        headCell.setBorder(new LineBorder(Color.RED, 2)); // Highlight head cell
        gbc.gridx = gridx++;
        tapePanel.add(headCell); // Placeholder for head cell

        for (int i = 0; i < cellAmountRightOfHead; i++) {
            var cell = new TapeCellUI("R");
            gbc.gridx = gridx++;
            tapePanel.add(cell, gbc); // Placeholder for right cells
            this.tapeCellsRight.add(cell); // Add to right cells list
        }
    }
}
