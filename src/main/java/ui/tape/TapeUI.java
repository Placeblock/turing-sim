package ui.tape;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TapeUI extends JPanel {
    private JPanel tapePanel;
    
    public TapeUI(int cellAmountLeftOfHead, int cellAmountRightOfHead) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        tapePanel = new JPanel(new GridBagLayout());
        
        JLabel arrowLabel = new JLabel("↓");
        arrowLabel.setForeground(Color.RED);
        
        add(tapePanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); // 5px space to the right of each cell

        int gridx = 0;
        for (int i = 0; i < cellAmountLeftOfHead; i++) {
            var cell = new TapeCell("L");
            gbc.gridx = gridx++;
            tapePanel.add(cell, gbc); // Placeholder for left cells
        }

        // Head cell
        var headCell = new TapeCell("H");
        headCell.setBorder(new LineBorder(Color.RED, 2)); // Highlight head cell
        gbc.gridx = gridx++;
        tapePanel.add(headCell); // Placeholder for head cell

        for (int i = 0; i < cellAmountRightOfHead; i++) {
            var cell = new TapeCell("R");
            gbc.gridx = gridx++;
            tapePanel.add(cell, gbc); // Placeholder for right cells
        }
    }
}
