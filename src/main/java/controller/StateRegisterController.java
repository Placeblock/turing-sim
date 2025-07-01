package controller;

import core.StateRegister;
import core.Transition;
import event.Receiver;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;
import event.events.TransitionChangeEvent;
import lombok.Getter;

public class StateRegisterController {
    @Getter
    private final Receiver receiver = new Receiver();

    private final StateRegister stateRegister;

    public StateRegisterController(StateRegister stateRegister) {
        this.stateRegister = stateRegister;

        this.receiver.registerHandler(TransitionChangeEvent.class, this::onTransitionChange);
        this.receiver.registerHandler(RemoveStateEvent.class, this::onRemoveState);
        this.receiver.registerHandler(AddStateEvent.class, this::onAddState);
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
