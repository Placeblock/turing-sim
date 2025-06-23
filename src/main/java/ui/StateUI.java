package ui;

import core.State;
import event.Emitter;
import event.events.AddStateEvent;

import javax.swing.*;
import java.util.List;

public class StateUI extends JPanel {
    private final Emitter<AddStateEvent> addStatePublisher;
    private final Emitter<AddStateEvent> removeStatePublisher;

    private final List<State> stateRegister;

    public StateUI(Emitter<AddStateEvent> addStatePublisher,
                   Emitter<AddStateEvent> removeStatePublisher,
                   List<State> stateRegister) {
        this.addStatePublisher = addStatePublisher;
        this.removeStatePublisher = removeStatePublisher;
        this.stateRegister = stateRegister;

        this.update();
    }

    private void update() {
        for (int i = 0; i < this.stateRegister.size(); i++) {
            this.add(new JLabel(Integer.toString(i)));
        }
    }

}
