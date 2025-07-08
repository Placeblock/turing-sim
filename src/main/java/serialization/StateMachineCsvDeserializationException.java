package serialization;

import java.io.IOException;

public class StateMachineCsvDeserializationException extends IOException { // Extend IOException cuz i'm funny
    public StateMachineCsvDeserializationException(String message) {
        super(message);
    }
}
