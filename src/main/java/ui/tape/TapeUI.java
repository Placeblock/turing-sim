package ui.tape;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import core.tape.Tape;
import core.tape.TapeCell;
import observer.events.TapeHeadPositionChangedEvent;
import observer.events.TapeLengthModifiedEvent;
import observer.events.TapeResetEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class TapeUI extends JScrollPane {

    public Tape<Character> tape;
    public JPanel tapePanel;

    private Map<TapeCell<Character>, TapeCellUI> cellToUIMap = new HashMap<>();
    private TapeCellUI headCellUI;
    
    public TapeUI(Tape<Character> tape) {
        super();
        this.tape = tape;

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setBorder(null);

        tapePanel = new JPanel(new GridBagLayout());
        setViewportView(tapePanel);

        rebuildTapeUI();

        tape.getHeadPositionChangedPublisher().subscribe(this::onHeadPositionChanged);
        tape.getLengthModifiedPublisher().subscribe(this::onTapeLengthModified);
        tape.getResetPublisher().subscribe(this::onReset);
    }

    /**
     * Rebuilds the tape UI by iterating through all tape cells
     * and updating the UI components accordingly.
     * It highlights the current head cell and resets highlights for others.
     */
    private void rebuildTapeUI() {
        var cells = tape.getAllCells();
        
        // Create a new map for the cells
        Map<TapeCell<Character>, TapeCellUI> newCellToUIMap = new HashMap<>();
        
        // Remove all components from the panel
        tapePanel.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2);
        
        int gridx = 0;
        for (TapeCell<Character> cell : cells) {
            TapeCellUI cellUI;
            
            // Reuse existing UI component if it exists, otherwise create new one
            cellUI = cellToUIMap.containsKey(cell) ? cellToUIMap.get(cell) : new TapeCellUI(cell);
            
            gbc.gridx = gridx++;
            tapePanel.add(cellUI, gbc);
            newCellToUIMap.put(cell, cellUI);
            
            cellUI.resetHighlight();
        }

        var head = tape.getHead();
        this.headCellUI = newCellToUIMap.get(head);
        this.headCellUI.highlight();
        
        // Update the cell to UI map
        this.cellToUIMap = newCellToUIMap;
        
        // Refresh the panel
        tapePanel.revalidate();
        tapePanel.repaint();
    }

    /**
     * Handles the TapeLengthModifiedEvent by rebuilding the tape UI.
     * This method is called when the tape length is modified,
     * ensuring that the UI reflects the current state of the tape.
     *
     * @param event The event indicating that the tape length has been modified.
     */
    private void onTapeLengthModified(TapeLengthModifiedEvent event) {
        rebuildTapeUI();
        
        // Scroll to head cell to maintain view
        SwingUtilities.invokeLater(this::scrollToHeadCell);
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

        SwingUtilities.invokeLater(this::scrollToHeadCell);
    }

    private void onReset(TapeResetEvent event) {
        this.rebuildTapeUI();
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
