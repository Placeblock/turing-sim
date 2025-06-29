package core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import observer.Publisher;
import observer.events.BlankSymbolChangedEvent;
import observer.events.InitialStateChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Configuration {
    private final Publisher<InitialStateChangedEvent> initialStateChangedPublisher = new Publisher<>();
    private final Publisher<TapeSymbolsChangedEvent> tapeSymbolsChangedPublisher = new Publisher<>();
    private final Publisher<BlankSymbolChangedEvent> blankSymbolChangedPublisher = new Publisher<>();

    private String initialState = "";
    private LinkedHashSet<Character> tapeSymbols = createTapeAlphabet();
    private Character blankSymbol = 'B';

    public Set<Character> getInputSymbols() {
        Set<Character> inputSymbols = new LinkedHashSet<>();
        for (char c : this.initialState.toCharArray()) {
            inputSymbols.add(c);
        }
        return inputSymbols;
    }

    private LinkedHashSet<Character> createTapeAlphabet() {
        LinkedHashSet<Character> alphabet = new LinkedHashSet<>();
        alphabet.add('0');
        alphabet.add('1');
        alphabet.add('B');
        alphabet.add('5');
        return alphabet;
    }

    public LinkedHashSet<Character> getTapeAlphabet() {
        return this.tapeSymbols;
    }
}
