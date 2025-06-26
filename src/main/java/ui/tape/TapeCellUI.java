package ui.tape;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import core.tape.TapeCell;
import observer.events.CellSymbolChangedEvent;

import java.awt.Color;
import java.awt.Dimension;

public class TapeCellUI extends JLabel {
    
    public TapeCellUI(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new LineBorder(Color.BLACK));
        setPreferredSize(new Dimension(25, 25));
    }

    public TapeCellUI() {
        this(""); // Default constructor with empty text
    }

    public TapeCellUI(TapeCell<Character> dataSource) {
        super(dataSource.getSymbol().toString());
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new LineBorder(Color.BLACK));
        setPreferredSize(new Dimension(25, 25));
        dataSource.getSymbolChangedPublisher().subscribe(this::onCellUpdate);
    }

    private void onCellUpdate(CellSymbolChangedEvent<Character> event) {
        setText(event.newSymbol().toString());
    }
}
