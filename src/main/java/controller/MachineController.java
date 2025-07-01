package controller;

import core.Machine;
import event.Receiver;
import event.events.StepEvent;
import lombok.Getter;

public class MachineController {
    @Getter
    private final Receiver receiver = new Receiver();
    private final Machine machine;

    public MachineController(Machine machine) {
        this.machine = machine;
        this.receiver.registerHandler(StepEvent.class, this::handleStepEvent);
    }

    private void handleStepEvent(StepEvent event) {
        this.machine.step();
    }

}
