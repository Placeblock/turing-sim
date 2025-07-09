package serialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

import core.Configuration;
import core.State;
import core.StateRegister;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Serializer class for converting a Configuration and StateRegister to and from a text format.
 * This class provides methods to serialize a Configuration and StateRegister to an output stream
 * and deserialize a Configuration and StateRegister from an input stream.
 */
public class ConfigSerializer {
    /**
     * A simple container class to hold a Configuration and its associated StateRegister.
     */
    @Getter
    @AllArgsConstructor
    public static class ConfigAndStateRegister {
        private final Configuration config;
        private final StateRegister register;
    }

    /**
     * Serializes the given Configuration and StateRegister to the specified output stream.
     * The output format includes the tape alphabet, blank symbol, initial state, and transition function.
     *
     * @param config the Configuration to serialize
     * @param register the StateRegister containing states to serialize
     * @param outputStream the OutputStream to write the serialized data to
     * @throws IOException if an I/O error occurs during stream writing
     */
    public static void serialize(Configuration config, StateRegister register, OutputStream outputStream) throws IOException {
        try (var writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            String outputStr = String.format(
                "# Blank Zeichen\n"+
                "%s\n\n"+
                "# Anfangs state\n"+
                "%s\n\n"+
                "# Übergangsfunktion\n",
                config.getBlankSymbol(),
                register.indexOf(config.getInitialState())
            );
            writer.write(outputStr);
            StateMachineCsvSerializer.serialize(register.getStates(), writer);
        }
    }

    /**
     * Deserializes a Configuration and StateRegister from the specified input stream.
     * The input format is expected to include the tape alphabet, blank symbol, initial state, and transition function.
     *
     * @param inputStream the InputStream to read the serialized data from
     * @return a ConfigAndStateRegister object containing the deserialized Configuration and StateRegister
     * @throws IOException if an I/O error occurs during stream reading
     */
    public static ConfigAndStateRegister deserialize(InputStream inputStream) throws IOException {
        String blankSymbolLine, initialStateLine;
        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            reader.readLine(); // # Blank Zeichen
            blankSymbolLine = reader.readLine();
            reader.readLine(); // Skip
            reader.readLine(); // # Anfangs state
            initialStateLine = reader.readLine();
            reader.readLine(); // Skip
            reader.readLine(); // # Übergangsfunktion

            var blankSymbol = blankSymbolLine.charAt(0);
            var initialStateIndex = Integer.parseInt(initialStateLine);

            var states = StateMachineCsvSerializer.deserialize(reader);

            var tapeAlphabet = new HashSet<Character>();
            for (State state : states) {
                tapeAlphabet.addAll(state.getTransitions().keySet());
            }

            var initialState = states.get(initialStateIndex);

            var config = new Configuration("", tapeAlphabet, blankSymbol, initialState); // initialTapeState set later when loading tape.txt
            var stateRegister = new StateRegister(states);

            return new ConfigAndStateRegister(config, stateRegister);
        }
    }
}
