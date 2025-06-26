package ui.tape;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import core.tape.Tape;
import core.tape.TapeCell;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TapeUI extends JPanel {

    public TapeCell<Character> aaa;
    
    public TapeUI( Tape<Character> tape) {
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

            if (cell.equals(head)) {
                cellUI.setBorder(new LineBorder(Color.RED, 2)); // Highlight head cell
                this.aaa = cell; // Store the head cell reference
            }
        }
    }
}
