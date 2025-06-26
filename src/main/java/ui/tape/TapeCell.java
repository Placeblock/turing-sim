package ui.tape;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

public class TapeCell extends JLabel {
    
    public TapeCell(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new LineBorder(Color.BLACK));
        setPreferredSize(new Dimension(25, 25));
    }
}
