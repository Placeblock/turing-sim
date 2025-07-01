package ui.stateregister;

import javax.swing.*;

public class StateRegisterPopupMenu extends JPopupMenu {

    public StateRegisterPopupMenu(int row, int column) {
        // TODO add more menu items
        // TODO events
        super();
        JMenuItem removeItem;
        if(row > 0 && column == 0) {
            removeItem = new JMenuItem("Remove State");
            removeItem.addActionListener(e -> {
                // TODO
                // Fire remove event
                // Example: removeStatePublisher.emit(new RemoveStateEvent(...));
            });
            this.add(removeItem);
        }
        if(row > 0 && column > 0) {
            removeItem = new JMenuItem("Remove Transition");
            removeItem.addActionListener(e -> {
                // TODO
                // Fire remove event
            });
            this.add(removeItem);
        }
        if(row == 0 && column > 0) {
            removeItem = new JMenuItem("Remove Symbol");
            removeItem.addActionListener(e -> {
                // TODO
                // Fire remove event
            });
            this.add(removeItem);
        }

    }
}
