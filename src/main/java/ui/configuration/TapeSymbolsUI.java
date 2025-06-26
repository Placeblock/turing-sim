package ui.configuration;

import core.Configuration;
import event.Emitter;
import event.Receiver;
import event.events.TapeSymbolsChangeEvent;
import observer.events.TapeSymbolsChangedEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TapeSymbolsUI extends JPanel {
    private final Emitter<TapeSymbolsChangeEvent> tapeSymbolsChangeEmitter;
    private final JTextField tapeSymbolsField;

    public TapeSymbolsUI(Configuration config, Receiver receiver) {
        super(new BorderLayout());

        this.tapeSymbolsChangeEmitter = new Emitter<>(receiver);
        this.tapeSymbolsField = new JTextField();

        this.updateTapeSymbols(config.getTapeSymbols());
        config.getTapeSymbolsChangedPublisher().subscribe(this::updateTapeSymbols);
        this.tapeSymbolsField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                Set<Character> chars = TapeSymbolsUI.this.getChars();
                TapeSymbolsChangeEvent event = new TapeSymbolsChangeEvent(chars);
                TapeSymbolsUI.this.tapeSymbolsChangeEmitter.emit(event);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {}

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {}
        });

        this.add(new JLabel("Tape Symbols"), BorderLayout.WEST);
        this.add(this.tapeSymbolsField, BorderLayout.CENTER);
    }

    public Set<Character> getChars() {
        Set<Character> chars = new HashSet<>();
        for (char c : this.tapeSymbolsField.getText().toCharArray()) {
            chars.add(c);
        }
        return chars;
    }

    public void updateTapeSymbols(TapeSymbolsChangedEvent event) {
        this.updateTapeSymbols(event.getTapeSymbols());
    }
    public void updateTapeSymbols(Set<Character> tapeSymbols) {
        String text = tapeSymbols.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        this.tapeSymbolsField.setText(text);
    }
}
