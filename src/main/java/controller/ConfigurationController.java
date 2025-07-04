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
        this.receiver.registerHandler(InitialTapeStateChangeEvent.class, this::handleInitialTapeStateChangeEvent);
        this.receiver.registerHandler(RemoveSymbolFromTapeAlphabetEvent.class, this::handleRemoveSymbolFromTapeAlphabetEvent);
        this.receiver.registerHandler(InitialStateChangeEvent.class, this::handleInitialStateChangeEvent);

        this.receiver.registerHandler(TransitionCreateEvent.class, this::handleCreateTransitionEvent);
        this.receiver.registerHandler(TransitionChangeEvent.class, this::handleChangeTransitionEvent);
        this.receiver.registerHandler(RemoveTransitionEvent.class, this::handleRemoveTransitionEvent);
        this.receiver.registerHandler(RemoveStateEvent.class, this::handleRemoveStateChangeEvent);
        this.receiver.registerHandler(AddStateEvent.class, this::handleAddStateChangeEvent);
        this.receiver.registerHandler(SaveTapeEvent.class, this::handleSaveTapeChangeEvent);
        this.receiver.registerHandler(AddSymbolToTapeAlphabetEvent.class, this::handleAddSymbolToTapeAlphabetEvent);
        this.receiver.registerHandler(TerminateStateEvent.class, this::handleTerminateStateEvent);
    }

    private void handleTerminateStateEvent(TerminateStateEvent terminateStateEvent) {
        System.out.println("Handling TerminateStateEvent for state: " + terminateStateEvent.state());
        this.stateRegister.getState(terminateStateEvent.state())
                .setTerminates(terminateStateEvent.terminates());
    }

    private void handleRemoveSymbolFromTapeAlphabetEvent(RemoveSymbolFromTapeAlphabetEvent event) {
        Set<Character> newTapeSymbols = config.getTapeAlphabet();
        newTapeSymbols.remove(event.symbol());
        this.updateTransitionSymbols(newTapeSymbols);
        System.out.println("Removing symbol from tape alphabet: " + event.symbol());
        this.config.setTapeAlphabet(newTapeSymbols);
    }


    private void handleTapeSymbolsChangeEvent(TapeSymbolsChangeEvent event) {
        Set<Character> newTapeSymbols = event.getSymbols();
        this.updateTransitionSymbols(event.getSymbols());
        this.config.setTapeAlphabet(newTapeSymbols);
    }

    private void handleAddSymbolToTapeAlphabetEvent(AddSymbolToTapeAlphabetEvent event) {
        Set<Character> newTapeSymbols = config.getTapeAlphabet();
        newTapeSymbols.add(event.symbol());
        this.updateTransitionSymbols(newTapeSymbols);
        System.out.println("Adding symbol to tape alphabet: " + event.symbol());
        this.config.setTapeAlphabet(newTapeSymbols);
    }

    private void handleInitialStateChangeEvent(InitialStateChangeEvent event) {
        this.config.setInitialState(event.initialState());
    }

    private void updateTransitionSymbols(Set<Character> symbols) {
        for (State state : this.stateRegister.getStates()) {
            state.updateSymbols(symbols);
        }
    }

    private void handleInitialTapeStateChangeEvent(InitialTapeStateChangeEvent event) {
        this.config.setInitialTapeState(event.getInitialState());
    }

    private void handleBlankSymbolChangeEvent(BlankSymbolChangeEvent event) {
        this.config.setBlankSymbol(event.getSymbol());
    }



    private void handleAddStateChangeEvent(AddStateEvent event) {
        System.out.println("ADDING STATE: " + event.index());
        stateRegister.addState(event.index());
    }

    private void handleRemoveStateChangeEvent(RemoveStateEvent event) {
        System.out.println("REMOVING STATE: " + stateRegister.getStates().indexOf(event.state()));
        stateRegister.removeState(event.state());
    }

    private void handleCreateTransitionEvent(TransitionCreateEvent event) {
        event.state().addTransition(event.symbol(), event.transition());
    }

    private void handleChangeTransitionEvent(TransitionChangeEvent event) {
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

    private void handleRemoveTransitionEvent(RemoveTransitionEvent event) {
        event.state().removeTransition(event.symbol());
    }

    private void handleSaveTapeChangeEvent(SaveTapeEvent event) {
        System.out.println("SaveTapeEvent received, but not implemented yet.");
        //TODO
    }
}
