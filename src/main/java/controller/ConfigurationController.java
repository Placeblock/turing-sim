package controller;

import core.Configuration;
import event.Receiver;
import event.events.BlankSymbolChangeEvent;
import event.events.InitialStateChangeEvent;
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
    }

    private void handleTapeSymbolsChangeEvent(TapeSymbolsChangeEvent event) {
        Set<Character> tapeSymbols = event.getSymbols();
        this.config.setTapeSymbols(tapeSymbols);
    }

    private void handleInitialStateChangeEvent(InitialStateChangeEvent event) {
        this.config.setInitialState(event.getInitialState());
    }

    private void handleBlankSymbolChangeEvent(BlankSymbolChangeEvent event) {
        this.config.setBlankSymbol(event.getSymbol());
    }
}
