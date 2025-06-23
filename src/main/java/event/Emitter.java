package event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Emitter<T extends Event> {
    private final Receiver receiver;

    public void emit(T event) {
        this.receiver.receive(event);
    }
}
