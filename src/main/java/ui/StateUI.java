package ui;

import core.StateRegister;
import event.Emitter;
import event.events.AddStateEvent;
import event.events.RemoveStateEvent;

import javax.swing.*;

public class StateUI extends JPanel{
    private final Emitter<AddStateEvent> addStatePublisher;
    private final Emitter<RemoveStateEvent> removeStatePublisher;

    private final StateRegister stateRegister;

    public StateUI(Emitter<AddStateEvent> addStatePublisher,
                   Emitter<RemoveStateEvent> removeStatePublisher,
                   StateRegister stateRegister) {
        this.addStatePublisher = addStatePublisher;
        this.removeStatePublisher = removeStatePublisher;
        this.stateRegister = stateRegister;

        this.stateRegister.getAddStatePublisher().subscribe(this::onStateAdd);
        this.stateRegister.getRemoveStatePublisher().subscribe(this::onStateRemove);
    }

    public void onStateAdd(observer.events.AddStateEvent event) {
        // Add UI State
    }
    public void onStateRemove(observer.events.RemoveStateEvent event) {
        // Remove UI State
    }
}
