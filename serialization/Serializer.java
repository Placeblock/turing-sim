package serialization;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import core.State;

public class Serializer {
    
    /**
     * Serializes a list of states to a specified output stream.
     * Each state is represented by its transitions and whether it is a terminating state.
     *
     * @param states the list of states to serialize
     * @param outputStream the output stream to write the serialized data to
     * @throws IOException if an I/O error occurs during stream writing
     */
    public static void serialize(List<State> states, OutputStream outputStream) throws IOException {
        try (var writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            int i = 0;
            for (var state : states) {
                if(state.getTransitions().isEmpty()) {
                    String line = String.format("%d,,,,,%b\n",
                            i,
                            state.isTerminating()
                        );
                    writer.write(line);
                    i++;
                    continue;
                }
                for (var entry : state.getTransitions().entrySet()) {
                    var symbol = entry.getKey();
                    var transition = entry.getValue();
                    String line = String.format("%d,%s,%s,%s,%d,%b\n",
                            i,
                            symbol,
                            transition.getNewSymbol(),
                            transition.getMove(),
                            states.indexOf(transition.getNewState()),
                            state.isTerminating()
                        );
                    writer.write(line);
                }
                i++;
            }
        }
    }
}
