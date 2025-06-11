package serialization;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import core.State;

public class Serializer {
    public static void serialize(List<State> states, BufferedOutputStream outputStream) throws IOException {
        int i = 0;
        for (var state : states) {
            for (var entry : state.getTransitions().entrySet()) {
                var symbol = entry.getKey();
                var transition = entry.getValue();
                String line = String.format("%d,%s,%s,%s,%s,%b\n",
                        i,
                        symbol,
                        transition.getNewSymbol(),
                        transition.getMove(),
                        states.indexOf(transition.getNewState()),
                        state.isTerminating()
                    );
                outputStream.write(line.getBytes());
            }
            i++;
        }
    }
}
