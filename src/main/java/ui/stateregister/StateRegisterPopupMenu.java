package ui.stateregister;

import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import event.events.*;

import java.awt.Component;

import javax.swing.*;

public class StateRegisterPopupMenu extends JPopupMenu {

    public StateRegisterPopupMenu(Receiver receiver, StateRegister stateRegister, Object o, int row, Component parent) {
        super();

        JMenuItem addItem = new JMenuItem("Add State after q" + (row - 1));
        addItem.addActionListener(e -> {
            AddStateEvent event = new AddStateEvent(row);
            receiver.receive(event);
        });
        this.add(addItem);
        JMenuItem removeItem;
        if(o instanceof State state) {
            JMenuItem terminateItem = new JMenuItem();
            updateTerminateItemText(terminateItem, state.isTerminates());
            terminateItem.addActionListener(e -> {
                boolean newState = !state.isTerminates();
                TerminateStateEvent event = new TerminateStateEvent(state, newState);
                receiver.receive(event);
                updateTerminateItemText(terminateItem, newState);
                parent.repaint();
            });
            this.add(terminateItem);
            removeItem = new JMenuItem("Remove State");
            removeItem.addActionListener(e -> {
                RemoveStateEvent event = new RemoveStateEvent(state);
                receiver.receive(event);
            });
            this.add(removeItem);
            if (!state.isTerminates()) {
                JMenuItem initialState = new JMenuItem("Set Initial State");
                initialState.addActionListener(e -> {
                    InitialStateChangeEvent event = new InitialStateChangeEvent(state);
                    receiver.receive(event);
                });
                this.add(initialState);
            }
        }
        if(o instanceof  Transition transition) {
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
                    return;
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
    private void updateTerminateItemText(JMenuItem item, boolean isFinal) {
        item.setText(isFinal ? "Change to Non-Final State" : "Change to Final State");
    }
}
