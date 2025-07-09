package serialization;



import core.Move;
import core.State;
import core.Transition;

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
            serialize(states, writer);
        }
    }

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
    public static void serialize(List<State> states, BufferedWriter writer) throws IOException {
        int i = 0;
        for (var state : states) {
            // Handle states with no transitions (typically terminating states)
            if(state.getTransitions().isEmpty()) {
                String line = String.format("%d,,,,,%b",
                        i,
                        state.isTerminates()
                    );
                writer.write(line);
                writer.newLine();
                i++;
                continue;
            }
            // Write one line per transition: stateIndex,inputSymbol,outputSymbol,move,newStateIndex,terminates
            for (var entry : state.getTransitions().entrySet()) {
                var symbol = entry.getKey();
                var transition = entry.getValue();
                String line = String.format("%d,%s,%s,%s,%d,%b",
                        i,
                        symbol,
                        transition.getNewSymbol(),
                        transition.getMove(),
                        states.indexOf(transition.getNewState()),
                        state.isTerminates()
                    );
                writer.write(line);
                writer.newLine();
            }
            i++;
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
        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return deserialize(reader);
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
     * @throws StateMachineCsvDeserializationException if the CSV format is invalid
     */
    public static List<State> deserialize(BufferedReader reader) throws IOException {
        var states = new ArrayList<State>();
        // Store transitions that need their target state set after all states are created
        var transitionLateinitNewStateIndexMap = new HashMap<Transition, Integer>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 6) throw new StateMachineCsvDeserializationException("Invalid line format: " + line);

            int stateIndex = Integer.parseInt(parts[0]);

            // Create state if it doesn't exist yet (states may be referenced before being defined)
            State state;
            if (stateIndex >= states.size()) {
                var terminates = Boolean.parseBoolean(parts[5]);
                state = new State(new java.util.HashMap<>(), terminates);
                states.add(state);
            } else {
                state = states.get(stateIndex);
            }

            // Skip transition creation for terminating states (empty transition fields)
            if (parts[1].isEmpty() && parts[2].isEmpty() && parts[3].isEmpty() && parts[4].isEmpty()) continue;

            // Parse transition components
            char symbol = parts[1].charAt(0); // input symbol
            char newSymbol = parts[2].charAt(0); // output symbol
            Move move = Move.valueOf(parts[3].toUpperCase()); // movement direction
            int newStateIndex = Integer.parseInt(parts[4]); // target state index

            // Create transition with null target state (will be set later)
            var transition = new Transition(newSymbol, move, null);
            state.getTransitions().put(symbol, transition);

            // Since the new state object might not exist yet, the transition and its newStateIndex is stored for later initialization
            transitionLateinitNewStateIndexMap.put(transition, newStateIndex);
        }
        
        // Second pass: link all transitions to their target states
        for (var entry : transitionLateinitNewStateIndexMap.entrySet()) {
            Transition transition = entry.getKey();
            int newStateIndex = entry.getValue();
            if (newStateIndex < states.size()) {
                transition.setNewState(states.get(newStateIndex));
            } else {
                throw new StateMachineCsvDeserializationException("Invalid new state index: " + newStateIndex);
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
