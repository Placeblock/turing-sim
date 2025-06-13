package serialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Move;
import core.State;
import core.Transition;

// TODO rename class to StateMachineCsvSerializer or similar
/**
 * Serializer class for converting a list of states to and from a CSV format.
 * This class provides methods to serialize a list of states to an output stream
 * and deserialize a list of states from an input stream.
 */
public class StateMachineCsvSerializer {
    
    /**
     * Serializes a list of states to a specified output stream in CSV format.
     * Each state is represented by its transitions and whether it is a terminating state.
     * <p>
     * Example output format:
     * <pre>
     *0,0,1,RIGHT,0,false
     *0,B,B,NONE,1,false
     *1,,,,,true
     * </pre>
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
                    String line = String.format("%d,,,,,%b",
                            i,
                            state.isTerminating()
                        );
                    writer.write(line);
                    writer.newLine();
                    i++;
                    continue;
                }
                for (var entry : state.getTransitions().entrySet()) {
                    var symbol = entry.getKey();
                    var transition = entry.getValue();
                    String line = String.format("%d,%s,%s,%s,%d,%b",
                            i,
                            symbol,
                            transition.getNewSymbol(),
                            transition.getMove(),
                            states.indexOf(transition.getNewState()),
                            state.isTerminating()
                        );
                    writer.write(line);
                    writer.newLine();
                }
                i++;
            }
        }
    }

    /**
     * Deserializes a list of states in CSV format to a list of states with transitions.
     * <p>
     * Example input format:
     * <pre>
     *0,0,1,RIGHT,0,false
     *0,B,B,NONE,1,false
     *1,,,,,true
     * </pre>
     * 
     * @param inputStream the input stream containing the serialized states in CSV format
     * @return a list of states with their transitions
     * @throws IOException if an I/O error occurs during stream reading
     */
    public static List<State> deserialize(InputStream inputStream) throws IOException {
        var states = new ArrayList<State>();
        var transitionLateinitNewStateIndexMap = new HashMap<Transition, Integer>();

        // Read the input stream line by line
        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) throw new IOException("Invalid line format: " + line); // TODO replace with custom exception

                int stateIndex = Integer.parseInt(parts[0]);

                // If state not yet present, create a new state in the list
                State state;
                if (stateIndex >= states.size()) {
                    var terminates = Boolean.parseBoolean(parts[5]);
                    state = new State(new java.util.HashMap<>(), terminates);
                    states.add(state);
                } else {
                    state = states.get(stateIndex);
                }

                // If transition is not present (State is terminating, therefore no transitions)
                if (parts[1].isEmpty() && parts[2].isEmpty() && parts[3].isEmpty() && parts[4].isEmpty()) continue;

                // Parse transition details
                char symbol = parts[1].charAt(0);
                char newSymbol = parts[2].charAt(0);
                Move move = Move.valueOf(parts[3].toUpperCase());
                int newStateIndex = Integer.parseInt(parts[4]);

                var transition = new Transition(newSymbol, move, null);
                state.getTransitions().put(symbol, transition);

                // Since the new state object might not exist yet, the transition and its newStateIndex is stored for later initialization
                transitionLateinitNewStateIndexMap.put(transition, newStateIndex);
            }
        }

        // Now that all states and transitions are created, we can fill in the newState for each transition
        for (var entry : transitionLateinitNewStateIndexMap.entrySet()) {
            Transition transition = entry.getKey();
            int newStateIndex = entry.getValue();
            if (newStateIndex < states.size()) {
                transition.setNewState(states.get(newStateIndex));
            } else {
                throw new IOException("Invalid new state index: " + newStateIndex); // TODO replace with custom exception
            }
        }

        return states;
    }

    public static void main(String[] args) {
        String asd = """
                    0,0,1,RIGHT,0,false
                    0,B,B,NONE,1,false
                    1,,,,,true
                    """;

        List<State> states = new ArrayList<>();

        try (InputStream inputStream = new java.io.ByteArrayInputStream(asd.getBytes(StandardCharsets.UTF_8))) {
            states = deserialize(inputStream);
            System.out.println(asd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            serialize(states, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
