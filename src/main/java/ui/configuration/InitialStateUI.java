package ui.configuration;

import core.Configuration;
import event.Emitter;
import event.Receiver;
import event.events.InitialStateChangeEvent;
import observer.events.InitialStateChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Set;

public class InitialStateUI extends JPanel {
    private final Emitter<InitialStateChangeEvent> initialStateChangeEmitter;

    private final JTextField textField;

    private boolean updatingContent = false;

    public InitialStateUI(Configuration config, Receiver receiver) {
        this.initialStateChangeEmitter = new Emitter<>(receiver);
        this.textField = new JTextField();

        config.getInitialStateChangedPublisher().subscribe(this::updateInitialState);
        config.getTapeSymbolsChangedPublisher().subscribe(this::updateTapeSymbols);

        this.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                String text = InitialStateUI.this.textField.getText();
                InitialStateUI.this.onUpdateInitialState(text);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                String text = InitialStateUI.this.textField.getText();
                InitialStateUI.this.onUpdateInitialState(text);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });

        this.add(new JLabel("Initial State: "));
        this.add(this.textField);
    }

    private void onUpdateInitialState(String initialState) {
        if (this.updatingContent) return;
        System.out.println("UPDATING INITIAL STATE: " + initialState);
        InitialStateChangeEvent event = new InitialStateChangeEvent(initialState);
        this.initialStateChangeEmitter.emit(event);
    }

    private void updateInitialState(InitialStateChangedEvent event) {
        if (this.updatingContent) return;
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            System.out.println("RECEIVED INITIAL STATE: " + event.getInitialState());
            this.textField.setText(event.getInitialState());
            this.updatingContent = false;
        });
    }

    private void updateTapeSymbols(TapeSymbolsChangedEvent event) {
        this.updateTapeSymbols(event.getSymbols());
    }
    private void updateTapeSymbols(Set<Character> tapeSymbols) {
        System.out.println("Updating Tape Symbols for initial State");
        String text = this.textField.getText();
        StringBuilder newText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (!tapeSymbols.contains(c)) continue;
            newText.append(c);
        }
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.textField.setText(newText.toString());
            this.updatingContent = false;
        });
    }
}
