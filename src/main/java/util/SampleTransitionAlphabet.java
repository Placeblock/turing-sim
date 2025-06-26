package util;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SampleTransitionAlphabet {
    public static LinkedHashSet<Character> get(){
        return (LinkedHashSet<Character>) Set.of('A', 'B', 'C');
    }
}
