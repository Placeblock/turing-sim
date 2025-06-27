package util;

import java.util.LinkedHashSet;

public class SampleTransitionAlphabet {
    public static LinkedHashSet<Character> get(){
        LinkedHashSet<Character> transitionAlphabet = new LinkedHashSet<>();
        transitionAlphabet.add('0');
        transitionAlphabet.add('1');
        transitionAlphabet.add('2');
        transitionAlphabet.add('B');

        return transitionAlphabet;
    }
}
