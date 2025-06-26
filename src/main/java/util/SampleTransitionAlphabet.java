package util;

import java.util.LinkedHashSet;
import java.util.Set;

public class SampleTransitionAlphabet {
    public static LinkedHashSet<Character> get(){
        LinkedHashSet<Character> transitionAlphabet = new LinkedHashSet<>();
        transitionAlphabet.add('A');
        transitionAlphabet.add('B');
        transitionAlphabet.add('C');

        return transitionAlphabet;
    }
}
