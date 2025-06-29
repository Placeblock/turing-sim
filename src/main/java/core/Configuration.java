package core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import observer.Publisher;
import observer.events.BlankSymbolChangedEvent;
import observer.events.InitialStateChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

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

    public void setTapeSymbols(LinkedHashSet<Character> tapeSymbols) {
        if (tapeSymbols.isEmpty()) {
            tapeSymbols.add('B');
        }
        this.tapeSymbols = tapeSymbols;
        this.tapeSymbolsChangedPublisher.publish(new TapeSymbolsChangedEvent(tapeSymbols));
        if (!this.tapeSymbols.contains(this.blankSymbol)) {
            System.out.println("Setting blank symbol because " + this.blankSymbol + " is not a valid symbol");
            this.setBlankSymbol(this.tapeSymbols.iterator().next());
        }
    }

    public void setBlankSymbol(Character blankSymbol) {
        if (!this.tapeSymbols.contains(blankSymbol)) {
            throw new IllegalArgumentException("Tried to set invalid blank symbol not in tape symbols");
        }
        System.out.println("SETTING BLANK SYMBOL: " + blankSymbol);
        this.blankSymbol = blankSymbol;
        this.blankSymbolChangedPublisher.publish(new BlankSymbolChangedEvent(blankSymbol));
    }

    public void setInitialState(String initialState) {
        this.initialState = this.removeInvalidCharacters(initialState);
        this.initialStateChangedPublisher.publish(new InitialStateChangedEvent(this.initialState));
    }

    private String removeInvalidCharacters(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!this.tapeSymbols.contains(c)) continue;
            result.append(c);
        }
        return result.toString();
    }

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
        return alphabet;
    }

    public LinkedHashSet<Character> getTapeAlphabet() {
        return this.tapeSymbols;
    }
}
