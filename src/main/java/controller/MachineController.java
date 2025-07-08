package controller;

import core.Configuration;
import core.Machine;
import core.MachineState;
import core.tape.Tape;
import event.Receiver;
import event.events.*;
import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class MachineController {
    @Getter
    private final Receiver receiver = new Receiver();
    private final Machine machine;
    private final Configuration config;

    private boolean running = false;

    private int interval = 100;
    private Timer timer = new Timer();

    public MachineController(Machine machine, Configuration config) {
        this.machine = machine;
        this.receiver.registerHandler(StepEvent.class, this::handleStepEvent);
        this.receiver.registerHandler(ResetEvent.class, this::handleResetEvent);
        this.receiver.registerHandler(StartEvent.class, this::handleStartEvent);
        this.receiver.registerHandler(StopEvent.class, this::handleStopEvent);
        this.receiver.registerHandler(IntervalEvent.class, this::handleIntervalEvent);
        this.config = config;
    }

    private void handleStartEvent(StartEvent event) {
        this.timer.cancel();
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MachineController.this.machine.step();
            }
        }, 0, this.interval);
        this.running = true;
    }
    private void handleStopEvent(StopEvent event) {
        this.timer.cancel();
        this.running = false;
    }
    private void handleIntervalEvent(IntervalEvent event) {
        this.timer.cancel();
        this.interval = event.interval();
        if (this.running) {
            this.handleStartEvent(new StartEvent());
        }
    }

    private void handleStepEvent(StepEvent event) {
        this.machine.step();
    }

    private void handleResetEvent(ResetEvent event) {
        this.timer.cancel();

        MachineState machineState = this.machine.getMachineState();
        machineState.setCurrentState(this.config.getInitialState());

        Tape<Character> tape = new Tape<>(config.getBlankSymbol(), config.getInitialTapeState());
        machineState.getTape().setDefaultSymbol(config.getBlankSymbol());
        machineState.getTape().reset(tape.getHead());

        if (this.running) {
            this.handleStartEvent(new StartEvent());
        }
    }

}
