package controller;

import core.Configuration;
import event.Receiver;
import event.events.BlankSymbolChangeEvent;
import event.events.InitialStateChangeEvent;
import event.events.RemoveSymbolFromTapeAlphabetEvent;
import event.events.TapeSymbolsChangeEvent;
import lombok.Getter;

import java.util.Set;

public class ConfigurationController {

    @Getter
    private final Receiver receiver = new Receiver();
    private final Configuration config;

    public ConfigurationController(Configuration config) {
        this.config = config;
        this.receiver.registerHandler(TapeSymbolsChangeEvent.class, this::handleTapeSymbolsChangeEvent);
        this.receiver.registerHandler(BlankSymbolChangeEvent.class, this::handleBlankSymbolChangeEvent);
        this.receiver.registerHandler(InitialStateChangeEvent.class, this::handleInitialStateChangeEvent);
        this.receiver.registerHandler(RemoveSymbolFromTapeAlphabetEvent.class, this::handleRemoveSymbolFromTapeAlphabetEvent);
    }

    private void handleRemoveSymbolFromTapeAlphabetEvent(RemoveSymbolFromTapeAlphabetEvent event) {
        Set<Character> newTapeSymbols = config.getTapeSymbols();
        newTapeSymbols.remove(event.symbol());
        System.out.println("Removing symbol from tape alphabet: " + event.symbol());
        this.config.setTapeSymbols(newTapeSymbols);
    }


    private void handleTapeSymbolsChangeEvent(TapeSymbolsChangeEvent event) {
        Set<Character> newTapeSymbols = event.getSymbols();
        this.config.setTapeSymbols(newTapeSymbols);
    }

    private void handleInitialStateChangeEvent(InitialStateChangeEvent event) {
        this.config.setInitialState(event.getInitialState());
    }

    private void handleBlankSymbolChangeEvent(BlankSymbolChangeEvent event) {
        this.config.setBlankSymbol(event.getSymbol());
    }
}
