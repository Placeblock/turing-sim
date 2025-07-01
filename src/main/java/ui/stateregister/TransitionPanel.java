package ui.stateregister;

import core.*;
import event.Emitter;
import event.Receiver;
import event.events.TransitionChangeEvent;
import lombok.RequiredArgsConstructor;
import observer.events.TransitionMoveChangedEvent;
import observer.events.TransitionStateChangedEvent;
import observer.events.TransitionSymbolChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TransitionPanel extends JPanel {
    private final Emitter<TransitionChangeEvent> transitionChangeEventEmitter;

    private final JComboBox<State> stateComboBox;
    private final JComboBox<Character> newSymbolComboBox;
    private final JComboBox<Move> moveComboBox;

    private boolean updatingContent = false;

    public TransitionPanel(Receiver receiver, StateRegister stateRegister, Configuration config, Transition transition) {
        this.transitionChangeEventEmitter = new Emitter<>(receiver);

        if (transition == null) {
            this.stateComboBox = null;
            this.newSymbolComboBox = null;
            this.moveComboBox = null;
            this.add(new JLabel("-"));
            return;
        }

        this.stateComboBox = new JComboBox<>();
        this.stateComboBox.setRenderer(new StateComboBoxRenderer(stateRegister));
        for (State state : stateRegister.getStates()) {
            this.stateComboBox.addItem(state);
        }
        State newState = transition.getNewState();
        this.stateComboBox.setSelectedItem(newState);
        this.stateComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            State newNewState = (State) e.getItem();
            Transition newTransition = new Transition(
                    transition.getNewSymbol(),
                    transition.getMove(),
                    newNewState
            );
            TransitionChangeEvent event = new TransitionChangeEvent(transition, newTransition);
            this.transitionChangeEventEmitter.emit(event);
        });

        // (->q1/q2/q3/q4, B, R)
        this.newSymbolComboBox = new JComboBox<>();
        for(Character alphabetChar: config.getTapeAlphabet()){
            this.newSymbolComboBox.addItem(alphabetChar);
        }
        this.newSymbolComboBox.setSelectedItem(transition.getNewSymbol());
        this.newSymbolComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            Character newNewSymbol = (Character) e.getItem();
            Transition newTransition = new Transition(
                    newNewSymbol,
                    transition.getMove(),
                    transition.getNewState()
            );
            TransitionChangeEvent event = new TransitionChangeEvent(transition, newTransition);
            this.transitionChangeEventEmitter.emit(event);
        });

        this.moveComboBox = new JComboBox<>();
        for (Move move: Move.values()) {
            this.moveComboBox.addItem(move);
        }
        this.moveComboBox.setSelectedItem(transition.getMove());
        this.moveComboBox.addItemListener((e) -> {
            if (this.updatingContent || e.getStateChange() != ItemEvent.SELECTED) return;
            Move newMove = (Move) e.getItem();
            Transition newTransition = new Transition(
                    transition.getNewSymbol(),
                    newMove,
                    transition.getNewState()
            );
            TransitionChangeEvent event = new TransitionChangeEvent(transition, newTransition);
            this.transitionChangeEventEmitter.emit(event);
        });

        this.add(new JLabel("("));
        this.add(stateComboBox);
        this.add(newSymbolComboBox);
        this.add(moveComboBox);
        this.add(new JLabel(")"));

        transition.getStateChangedPublisher().subscribe(this::onStateChanged);
        transition.getSymbolChangedPublisher().subscribe(this::onSymbolChanged);
        transition.getMoveChangedPublisher().subscribe(this::onMoveChanged);
    }

    private void onStateChanged(TransitionStateChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.stateComboBox.setSelectedItem(event.getNewState());
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
