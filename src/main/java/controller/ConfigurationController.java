package controller;

import core.Configuration;
import core.State;
import core.StateRegister;
import core.Transition;
import event.Receiver;
import event.events.*;
import lombok.Getter;
import serialization.ConfigSerializer;

import javax.swing.*;
import java.util.Set;

/**
 * Controller responsible for managing configuration changes in the Turing machine.
 *
 * <p>This controller acts as the central coordinator for all configuration-related
 * events, including changes to the tape alphabet, blank symbol, initial state,
 * and state transitions. It ensures data consistency and coordinates updates
 * between the configuration, state register, and dependent components.</p>
 *
 * <p>The controller implements the event-driven architecture pattern, registering
 * handlers for various configuration events and coordinating the appropriate
 * responses to maintain system integrity.</p>
 *
 * @see Configuration
 * @see StateRegister
 * @see event.Receiver
 */
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
        this.receiver.registerHandler(SaveTapeEvent.class, this::handleSaveTapeEvent);
        this.receiver.registerHandler(SaveTransitionsEvent.class, this::handleSaveTransitionsEvent);
        this.receiver.registerHandler(AddSymbolToTapeAlphabetEvent.class, this::handleAddSymbolToTapeAlphabetEvent);
        this.receiver.registerHandler(TerminateStateEvent.class, this::handleTerminateStateEvent);
    }

    private void handleTerminateStateEvent(TerminateStateEvent terminateStateEvent) {
        System.out.println("Handling TerminateStateEvent for state: " + terminateStateEvent.state());
        if (this.config.getInitialState().equals(terminateStateEvent.state())) {
            JOptionPane.showMessageDialog(null,"Der Anfangszustand kann nicht zum Endzustand gemacht werden.","Fehler!",1);
            return;
        }
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
        if (event.initialState().isTerminates()) {
            JOptionPane.showMessageDialog(null,"Ein Endzustand kann nicht zum Anfangszustand gemacht werden.","Fehler!",1);
            return;
        }
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

    private void handleSaveTransitionsEvent(SaveTransitionsEvent event) {
        try (var outputStream = new java.io.FileOutputStream(event.file())) {
            ConfigSerializer.serialize(config, stateRegister, outputStream);
            System.out.println("Config saved to: " + event.file().getAbsolutePath());
        } catch (java.io.IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

    private void handleSaveTapeEvent(SaveTapeEvent event) {
        var tapeContent = config.getInitialTapeString();
        try (var writer = new java.io.BufferedWriter(new java.io.FileWriter(event.file()))) {
            writer.write(tapeContent);
            System.out.println("Tape saved to: " + event.file().getAbsolutePath());
        } catch (java.io.IOException e) {
            System.err.println("Error saving tape: " + e.getMessage());
        }
    }
}
