package util;

import core.State;
import serialization.StateMachineCsvSerializer;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SampleStateRegister {
    public static List<State> get(){
        List<State> states = new ArrayList<>();

        String asd = """
                    0,0,1,RIGHT,0,false
                    0,2,0,RIGHT,0,false
                    0,B,B,NONE,1,false
                    1,,,,,true
                    """;

        try (InputStream inputStream = new java.io.ByteArrayInputStream(asd.getBytes(StandardCharsets.UTF_8))) {
            states = StateMachineCsvSerializer.deserialize(inputStream);
            System.out.println(asd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return states;
    }
}
