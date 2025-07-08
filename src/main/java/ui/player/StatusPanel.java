package ui.player;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.StateRegister;
import observer.Publisher;
import observer.events.CurrentStateChangedEvent;

public class StatusPanel extends JPanel {
    private final StateRegister stateRegister;

    private JLabel stateLabel;
    private JLabel terminationLabel;
    
    public StatusPanel(Publisher<CurrentStateChangedEvent> currentStatePublisher, StateRegister stateRegister) {
        this.stateRegister = stateRegister;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        stateLabel = new JLabel("State: q0");
        terminationLabel = new JLabel("");

        currentStatePublisher.subscribe(this::onCurrentStateChanged);

        add(Box.createHorizontalStrut(10));
        add(stateLabel);
        add(Box.createHorizontalStrut(20));
        add(terminationLabel);
    }

    private void onCurrentStateChanged(CurrentStateChangedEvent event) {
        var i = stateRegister.getStates().indexOf(event.currentState());
        stateLabel.setText("State: q" + i);
        if(event.currentState().isTerminates()) {
            terminationLabel.setText("Machine terminated");
        } else {
            terminationLabel.setText("");
        }
    }
}
