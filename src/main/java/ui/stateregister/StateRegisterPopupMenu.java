package ui.stateregister;

import core.State;
import core.Transition;
import event.Receiver;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;
import event.events.RemoveSymbolFromTapeAlphabetEvent;
import event.events.TransitionChangeEvent;

import javax.swing.*;

public class StateRegisterPopupMenu extends JPopupMenu {

    public StateRegisterPopupMenu(Receiver receiver, Object o, int row) {
        super();

        // TODO: Add action listeners for addItem and terminateItem
        JMenuItem addItem = new JMenuItem("Add State");
        addItem.addActionListener(e -> {
            AddStateEvent event = new AddStateEvent(row + 1);
            receiver.receive(event);
        });
        this.add(addItem);
        JMenuItem removeItem;
        if(o instanceof State state) {
            JMenuItem terminateItem = new JMenuItem("Terminate State");
            this.add(terminateItem);
            this.add(addItem);
            removeItem = new JMenuItem("Remove State");
            removeItem.addActionListener(e -> {
                RemoveStateEvent event = new RemoveStateEvent(state);
                receiver.receive(event);
            });
            this.add(removeItem);
        }
        if(o instanceof  Transition transition) {
            JMenuItem terminateItem = new JMenuItem("Terminate State");
            this.add(terminateItem);
            removeItem = new JMenuItem("Remove Transition");
            removeItem.addActionListener(e -> {
                TransitionChangeEvent event = new TransitionChangeEvent(transition, null);
                receiver.receive(event);
            });
            this.add(removeItem);
        }
        if(o instanceof Character symbol) {
            removeItem = new JMenuItem("Remove Symbol");
            removeItem.addActionListener(e -> {
                RemoveSymbolFromTapeAlphabetEvent event = new RemoveSymbolFromTapeAlphabetEvent(symbol);
                receiver.receive(event);
            });
            this.add(removeItem);
        }
    }
}
