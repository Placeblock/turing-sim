package ui.configuration;

import core.Configuration;
import event.Emitter;
import event.Receiver;
import event.events.BlankSymbolChangeEvent;
import observer.events.BlankSymbolChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.util.Set;

public class BlankSymbolComboBox extends JComboBox<Character> {
    private final Emitter<BlankSymbolChangeEvent> blankSymbolChangeEmitter;
    private final Configuration config;

    private boolean updatingContent = false;

    public BlankSymbolComboBox(Configuration config, Receiver receiver) {
        config.getBlankSymbolChangedPublisher().subscribe(this::updateBlankSymbol);
        config.getTapeSymbolsChangedPublisher().subscribe(this::updateTapeSymbols);

        this.blankSymbolChangeEmitter = new Emitter<>(receiver);

        this.config = config;

        this.addItemListener((event) -> {
            if (this.updatingContent || event.getStateChange() != ItemEvent.SELECTED) return;
            char symbol = (char) event.getItem();
            this.blankSymbolChangeEmitter.emit(new BlankSymbolChangeEvent(symbol));
        });

        this.updateTapeSymbols(this.config.getTapeAlphabet());
    }

    private void updateBlankSymbol(BlankSymbolChangedEvent event) {
        this.setSelectedItem(event.getSymbol());
    }

    private void updateTapeSymbols(TapeSymbolsChangedEvent event) {
        this.updateTapeSymbols(event.getSymbols());
    }
    private void updateTapeSymbols(Set<Character> tapeSymbols) {
        System.out.println("Updating Tape Symbols for Blank Symbol");

        javax.swing.SwingUtilities.invokeLater(() -> {
            this.updatingContent = true;
            this.removeAllItems();
            for (Character tapeSymbol : tapeSymbols) {
                this.addItem(tapeSymbol);
            }
            this.setSelectedItem(this.config.getBlankSymbol());
            this.updatingContent = false;
        });
    }

}
