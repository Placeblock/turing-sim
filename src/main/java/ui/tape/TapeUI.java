package ui.tape;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import core.tape.Tape;
import core.tape.TapeCell;
import observer.events.TapeHeadPositionChangedEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class TapeUI extends JScrollPane {

    public TapeCell<Character> aaa; //! TEST

    private Map<TapeCell<Character>, TapeCellUI> cellToUIMap = new HashMap<>();
    private TapeCellUI headCellUI;
    
    public TapeUI(Tape<Character> tape) {
        super();

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setPreferredSize(new Dimension(200, 50)); //! TEST

        JPanel tapePanel = new JPanel(new GridBagLayout());
        setViewportView(tapePanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); 
        
        var cells = tape.getAllCells();
        var head = tape.getHead();

        int gridx = 0;
        for (TapeCell<Character> cell : cells) {
            var cellUI = new TapeCellUI(cell);
            gbc.gridx = gridx++;
            tapePanel.add(cellUI, gbc);
            this.cellToUIMap.put(cell, cellUI);

            if (cell.equals(head)) {
                this.headCellUI = cellUI;
                this.headCellUI.highlight(); // Highlight head cell
                this.aaa = cell; //! TEST
            }
        }

        // Subscribe to head position changes
        tape.getHeadPositionChangedPublisher().subscribe(this::onHeadPositionChanged);
    }

    /**
     * Handles the TapeHeadPositionChangedEvent by updating the UI.
     * It resets the highlight on the old head cell and highlights the new head cell.
     * It also scrolls the view to ensure the head cell is visible.
     *
     * @param event The event containing the new head position.
     */
    private void onHeadPositionChanged(TapeHeadPositionChangedEvent<Character> event) {
        this.headCellUI.resetHighlight(); // Reset highlight on old head cell
        this.headCellUI = this.cellToUIMap.get(event.newHeadPosition());
        this.headCellUI.highlight(); // Highlight new head cell

        scrollToHeadCell();
    }

    /**
     * Scrolls the view to center the head cell in the viewport.
     * This method calculates the center position of the head cell
     * and adjusts the scroll position of the scroll pane
     * to ensure the head cell is centered.
     */
    private void scrollToHeadCell() {
        if (this.headCellUI != null) {
            Rectangle cellBounds = this.headCellUI.getBounds();
            Rectangle viewportBounds = this.getViewport().getViewRect();
            
            // Calculate the center position
            int cellCenterX = cellBounds.x + cellBounds.width / 2;
            int viewportCenterX = viewportBounds.width / 2;
            int targetScrollX = cellCenterX - viewportCenterX;
            
            // Don't scroll beyond bounds
            int maxScrollX = this.getHorizontalScrollBar().getMaximum() - viewportBounds.width;
            targetScrollX = Math.max(0, Math.min(targetScrollX, maxScrollX));
            
            this.getHorizontalScrollBar().setValue(targetScrollX);
        }
    }
}
