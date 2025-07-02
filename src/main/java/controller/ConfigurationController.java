package controller;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import event.events.*;
import lombok.Getter;

import java.util.Set;

public class ConfigurationController {

    @Getter
    private final Receiver receiver = new Receiver();
    private final Configuration config;
    private final StateRegister stateRegister;

    public ConfigurationController(Configuration config, StateRegister stateRegister) {
        this.config = config;
        this.stateRegister = stateRegister;

        this.receiver.registerHandler(TapeSymbolsChangeEvent.class, this::handleTapeSymbolsChangeEvent);
        this.receiver.registerHandler(BlankSymbolChangeEvent.class, this::handleBlankSymbolChangeEvent);
        this.receiver.registerHandler(InitialStateChangeEvent.class, this::handleInitialStateChangeEvent);
        this.receiver.registerHandler(RemoveSymbolFromTapeAlphabetEvent.class, this::handleRemoveSymbolFromTapeAlphabetEvent);

        this.receiver.registerHandler(TransitionChangeEvent.class, this::onTransitionChange);
        this.receiver.registerHandler(RemoveStateEvent.class, this::onRemoveState);
        this.receiver.registerHandler(AddStateEvent.class, this::onAddState);
    }

    private void handleRemoveSymbolFromTapeAlphabetEvent(RemoveSymbolFromTapeAlphabetEvent event) {
        Set<Character> newTapeSymbols = config.getTapeSymbols();
        newTapeSymbols.remove(event.symbol());
        this.updateTransitionSymbols(newTapeSymbols);
        System.out.println("Removing symbol from tape alphabet: " + event.symbol());
        this.config.setTapeSymbols(newTapeSymbols);
    }


    private void handleTapeSymbolsChangeEvent(TapeSymbolsChangeEvent event) {
        Set<Character> newTapeSymbols = event.getSymbols();
        this.updateTransitionSymbols(event.getSymbols());
        this.config.setTapeSymbols(newTapeSymbols);
    }

    private void updateTransitionSymbols(Set<Character> symbols) {
        for (State state : this.stateRegister.getStates()) {
            state.updateSymbols(symbols);
        }
    }

    private void handleInitialStateChangeEvent(InitialStateChangeEvent event) {
        this.config.setInitialState(event.getInitialState());
    }

    private void handleBlankSymbolChangeEvent(BlankSymbolChangeEvent event) {
        this.config.setBlankSymbol(event.getSymbol());
    }



    private void onAddState(AddStateEvent event) {
        stateRegister.addState(event.index());
    }

    private void onRemoveState(RemoveStateEvent event) {
        System.out.println("REMOVING STATE: " + stateRegister.getStates().indexOf(event.state()));
        stateRegister.removeState(event.state());
    }

    private void onTransitionChange(TransitionChangeEvent event) {
        System.out.println("UPDATING TRANSITION");

        Transition oldTransition = event.getOldTransition();
        Transition newTransition = event.getNewTransition();

        if (newTransition.getNewState() != oldTransition.getNewState()) {
            oldTransition.setNewState(newTransition.getNewState());
        }
        if (newTransition.getNewSymbol() != oldTransition.getNewSymbol()) {
            oldTransition.setNewSymbol(newTransition.getNewSymbol());
        }
        if (newTransition.getMove() != oldTransition.getMove()) {
            oldTransition.setMove(newTransition.getMove());
        }
    }
}
