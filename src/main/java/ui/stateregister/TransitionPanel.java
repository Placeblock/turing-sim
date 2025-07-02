package ui.stateregister;

import core.*;
import event.Receiver;
import lombok.RequiredArgsConstructor;
import observer.events.TransitionMoveChangedEvent;
import observer.events.TransitionStateChangedEvent;
import observer.events.TransitionSymbolChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TransitionPanel extends JPanel {

    private final JComboBox<State> stateComboBox;
    private final JComboBox<Character> newSymbolComboBox;
    private final JComboBox<Move> moveComboBox;

    private final Transition transition;

    private boolean updatingContent = false;

    public TransitionPanel(StateRegister stateRegister, Configuration config, Transition transition) {
        this.transition = transition;
        if (transition == null) {
            this.stateComboBox = null;
            this.newSymbolComboBox = null;
            this.moveComboBox = null;
            this.add(new JLabel("-"));
            return;
        }

        this.stateComboBox = new JComboBox<>();
        this.stateComboBox.setOpaque(true);
        this.stateComboBox.setRenderer(new StateComboBoxRenderer(stateRegister));
        this.stateComboBox.setEnabled(false); // Prevent bug

        for (State listState : stateRegister.getStates()) {
            this.stateComboBox.addItem(listState);
        }
        State newState = transition.getNewState();
        this.stateComboBox.setSelectedItem(newState);
        if (this.stateComboBox.getSelectedItem() == null) {
            this.setBackground(Color.RED);
        }
        this.stateComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            State newNewState = (State) e.getItem();
            this.transition.setNewState(newNewState);
        });

        // (->q1/q2/q3/q4, B, R)
        this.newSymbolComboBox = new JComboBox<>();
        this.newSymbolComboBox.setEnabled(false); // Prevent bug
        for (Character alphabetChar : config.getTapeAlphabet()) {
            this.newSymbolComboBox.addItem(alphabetChar);
        }
        this.newSymbolComboBox.setSelectedItem(transition.getNewSymbol());
        if (this.newSymbolComboBox.getSelectedItem() == null) {
            this.setBackground(Color.RED);
        }

        this.newSymbolComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            Character newNewSymbol = (Character) e.getItem();
            this.transition.setNewSymbol(newNewSymbol);
        });

        this.moveComboBox = new JComboBox<>();
        this.moveComboBox.setEnabled(false); // Prevent bug
        for (Move move : Move.values()) {
            this.moveComboBox.addItem(move);
        }
        this.moveComboBox.setSelectedItem(transition.getMove());
        this.moveComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            Move newMove = (Move) e.getItem();
            this.transition.setMove(newMove);
        });

        this.add(new JLabel("("));
        this.add(stateComboBox);
        this.add(newSymbolComboBox);
        this.add(moveComboBox);
        this.add(new JLabel(")"));

        // Enable combo boxes after the editor is properly established to prevent bug
        SwingUtilities.invokeLater(() -> {
            this.stateComboBox.setEnabled(true);
            this.newSymbolComboBox.setEnabled(true);
            this.moveComboBox.setEnabled(true);
        });

        transition.getStateChangedPublisher().subscribe(this::onStateChanged);
        transition.getSymbolChangedPublisher().subscribe(this::onSymbolChanged);
        transition.getMoveChangedPublisher().subscribe(this::onMoveChanged);
    }

    private void onStateChanged(TransitionStateChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.stateComboBox.setSelectedItem(event.getNewState());
            if (this.newSymbolComboBox.getSelectedItem() != null) {
                this.setBackground(Color.WHITE);
            }
            this.updatingContent = false;
        });
    }

    private void onMoveChanged(TransitionMoveChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.moveComboBox.setSelectedItem(event.getNewMove());
            this.updatingContent = false;
        });
    }

    private void onSymbolChanged(TransitionSymbolChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.newSymbolComboBox.setSelectedItem(event.getNewSymbol());
            if(this.stateComboBox.getSelectedItem() != null) {
                this.setBackground(Color.WHITE);
            }
            this.updatingContent = false;
        });
    }

    @RequiredArgsConstructor
    public static class StateComboBoxRenderer implements ListCellRenderer<State> {
        private final StateRegister stateRegister;

        @Override
        public Component getListCellRendererComponent(JList<? extends State> jList, State state, int i, boolean b, boolean b1) {
            int index = this.stateRegister.getStates().indexOf(state);
            return new JLabel("q" + index);
        }
    }
}
