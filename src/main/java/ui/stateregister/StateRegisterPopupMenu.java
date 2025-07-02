package ui.stateregister;

import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import event.events.*;

import javax.swing.*;

public class StateRegisterPopupMenu extends JPopupMenu {

    public StateRegisterPopupMenu(Receiver receiver, StateRegister stateRegister, Object o, int row) {
        super();

        // TODO: Add action listeners for addItem and terminateItem
        JMenuItem addItem = new JMenuItem("Add State");
        addItem.addActionListener(e -> {
            AddStateEvent event = new AddStateEvent(row);
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
                State state = stateRegister.getState(transition);
                if (state == null) {
                    System.out.println("State not found");
                    return;
                }
                Character symbol = state.getSymbol(transition);
                RemoveTransitionEvent event = new RemoveTransitionEvent(state, symbol);
                receiver.receive(event);
            });
            this.add(removeItem);
        }
        if(o instanceof Character symbol) {
            addItem = new JMenuItem("Add Symbol");
            addItem.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(
                        null,
                        "Enter new symbol:",
                        "Add Symbol",
                        JOptionPane.PLAIN_MESSAGE
                );
                if (input == null || input.isEmpty()) {
                    return; // User cancelled or entered nothing
                }
                if (input.length() == 1) {
                    AddSymbolToTapeAlphabetEvent event = new AddSymbolToTapeAlphabetEvent(input.charAt(0));
                    receiver.receive(event);
                }
                if(input.length() > 1) {
                    JOptionPane.showMessageDialog(null, "Please enter a single character symbol.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            });
            this.add(addItem);

            removeItem = new JMenuItem("Remove Symbol");
            removeItem.addActionListener(e -> {
                RemoveSymbolFromTapeAlphabetEvent event = new RemoveSymbolFromTapeAlphabetEvent(symbol);
                receiver.receive(event);
            });
            this.add(removeItem);
        }
    }
}
