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
import java.util.LinkedHashSet;
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
                LinkedHashSet<Character> chars = TapeSymbolsUI.this.getChars();
                TapeSymbolsUI.this.onUpdateTapeSymbols(chars);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                LinkedHashSet<Character> chars = TapeSymbolsUI.this.getChars();
                TapeSymbolsUI.this.onUpdateTapeSymbols(chars);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });

        this.add(new JLabel("Tape Symbols: "), BorderLayout.WEST);
        this.add(this.tapeSymbolsField, BorderLayout.CENTER);
    }

    public LinkedHashSet<Character> getChars() {
        LinkedHashSet<Character> chars = new LinkedHashSet<>();
        for (char c : this.tapeSymbolsField.getText().toCharArray()) {
            chars.add(c);
        }
        return chars;
    }

    private void onUpdateTapeSymbols(LinkedHashSet<Character> chars) {
        if (TapeSymbolsUI.this.updatingContent) return;
        TapeSymbolsChangeEvent event = new TapeSymbolsChangeEvent(chars);
        TapeSymbolsUI.this.tapeSymbolsChangeEmitter.emit(event);
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
