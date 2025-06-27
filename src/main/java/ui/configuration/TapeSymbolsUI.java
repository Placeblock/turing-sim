package ui.configuration;

import core.Configuration;
import event.Emitter;
import event.Receiver;
import observer.events.TapeSymbolsChangedEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TapeSymbolsUI extends JPanel {
    private final Emitter<event.events.TapeSymbolsChangeEvent> tapeSymbolsChangeEmitter;
    private final JTextField tapeSymbolsField;

    private boolean updatingContent = false;

    public TapeSymbolsUI(Configuration config, Receiver receiver) {
        super(new BorderLayout());

        this.tapeSymbolsChangeEmitter = new Emitter<>(receiver);
        this.tapeSymbolsField = new JTextField();

        this.updateTapeSymbols(config.getTapeSymbols());
        config.getTapeSymbolsChangedPublisher().subscribe(this::updateTapeSymbols);
        this.tapeSymbolsField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (TapeSymbolsUI.this.updatingContent) return;
                Set<Character> chars = TapeSymbolsUI.this.getChars();
                event.events.TapeSymbolsChangeEvent event = new event.events.TapeSymbolsChangeEvent(chars);
                TapeSymbolsUI.this.tapeSymbolsChangeEmitter.emit(event);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (TapeSymbolsUI.this.updatingContent) return;
                Set<Character> chars = TapeSymbolsUI.this.getChars();
                event.events.TapeSymbolsChangeEvent event = new event.events.TapeSymbolsChangeEvent(chars);
                TapeSymbolsUI.this.tapeSymbolsChangeEmitter.emit(event);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });

        this.add(new JLabel("Tape Symbols: "), BorderLayout.WEST);
        this.add(this.tapeSymbolsField, BorderLayout.CENTER);
    }

    public Set<Character> getChars() {
        Set<Character> chars = new HashSet<>();
        for (char c : this.tapeSymbolsField.getText().toCharArray()) {
            chars.add(c);
        }
        return chars;
    }

    private void updateTapeSymbols(TapeSymbolsChangedEvent event) {
        this.updateTapeSymbols(event.getSymbols());
    }
    private void updateTapeSymbols(Set<Character> tapeSymbols) {
        String text = tapeSymbols.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        if (text.equals(this.tapeSymbolsField.getText())) return;
        SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            tapeSymbolsField.setText(text);
            this.updatingContent = false;
        });
    }
}
