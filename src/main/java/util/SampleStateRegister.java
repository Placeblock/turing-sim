package util;

import core.State;
import core.StateRegister;
import serialization.StateMachineCsvSerializer;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SampleStateRegister {
    public static StateRegister get(){
        List<State> states = new ArrayList<>();

        String asd = """
                    0,0,1,RIGHT,0,false
                    0,1,0,RIGHT,0,false
                    0,B,B,LEFT,1,false
                    1,,,,,true
                    """;

        try (InputStream inputStream = new java.io.ByteArrayInputStream(asd.getBytes(StandardCharsets.UTF_8))) {
            states = StateMachineCsvSerializer.deserialize(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StateRegister(states);
    }
}
