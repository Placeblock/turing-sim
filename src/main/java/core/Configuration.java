package core;

import lombok.Getter;
import lombok.Setter;
import observer.Publisher;
import observer.events.BlankSymbolChangedEvent;
import observer.events.InitialStateChangedEvent;
import observer.events.InitialTapeStateChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Configuration {
    private final Publisher<InitialTapeStateChangedEvent> initialTapeStateChangedPublisher = new Publisher<>();
    private final Publisher<TapeSymbolsChangedEvent> tapeSymbolsChangedPublisher = new Publisher<>();
    private final Publisher<BlankSymbolChangedEvent> blankSymbolChangedPublisher = new Publisher<>();
    private final Publisher<InitialStateChangedEvent> initialStateChangedPublisher = new Publisher<>();

    private String initialTapeState = "";
    private Set<Character> tapeAlphabet = createTapeAlphabet();
    private Character blankSymbol = 'B';
    private State initialState;

    public Configuration(State initialState) {
        this.initialState = initialState;
    }

    public void setTapeAlphabet(Set<Character> tapeAlphabet) {
        if (tapeAlphabet.isEmpty()) {
            tapeAlphabet.add('B');
        }
        this.tapeAlphabet = tapeAlphabet;
        this.tapeSymbolsChangedPublisher.publish(new TapeSymbolsChangedEvent(tapeAlphabet));
        if (!this.tapeAlphabet.contains(this.blankSymbol)) {
            System.out.println("Setting blank symbol because " + this.blankSymbol + " is not a valid symbol");
            this.setBlankSymbol(this.tapeAlphabet.iterator().next());
        }
    }

    public void setBlankSymbol(Character blankSymbol) {
        if (!this.tapeAlphabet.contains(blankSymbol)) {
            throw new IllegalArgumentException("Tried to set invalid blank symbol not in tape symbols");
        }
        System.out.println("SETTING BLANK SYMBOL: " + blankSymbol);
        this.blankSymbol = blankSymbol;
        this.blankSymbolChangedPublisher.publish(new BlankSymbolChangedEvent(blankSymbol));
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
        this.initialStateChangedPublisher.publish(new InitialStateChangedEvent(initialState));
    }

    public void setInitialTapeState(String initialTapeState) {
        this.initialTapeState = this.removeInvalidCharacters(initialTapeState);
        this.initialTapeStateChangedPublisher.publish(new InitialTapeStateChangedEvent(this.initialTapeState));
    }

    private String removeInvalidCharacters(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.tapeAlphabet.contains(c)) continue;
            result.append(c);
        }
        return result.toString();
    }

    public Set<Character> getInputSymbols() {
        Set<Character> inputSymbols = new LinkedHashSet<>();
        for (char c : this.initialTapeState.toCharArray()) {
            inputSymbols.add(c);
        }
        return inputSymbols;
    }

    private Set<Character> createTapeAlphabet() {
        LinkedHashSet<Character> alphabet = new LinkedHashSet<>();
        alphabet.add('0');
        alphabet.add('1');
        alphabet.add('B');
        return alphabet;
    }

    public List<Character> getInitialTapeState() {
        List<Character> initialState = new ArrayList<>();
        for (char c : this.initialTapeState.toCharArray()) {
            initialState.add(c);
        }
        return initialState;
    }
}
