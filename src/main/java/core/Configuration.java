package core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import observer.Publisher;
import observer.events.BlankSymbolChangedEvent;
import observer.events.InitialStateChangedEvent;
import observer.events.TapeSymbolsChangedEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Configuration {
    private final Publisher<InitialStateChangedEvent> initialStateChangedPublisher = new Publisher<>();
    private final Publisher<TapeSymbolsChangedEvent> tapeSymbolsChangedPublisher = new Publisher<>();
    private final Publisher<BlankSymbolChangedEvent> blankSymbolChangedPublisher = new Publisher<>();

    private String initialState = "";
    private Set<Character> tapeSymbols = Set.of('B');
    private Character blankSymbol = 'B';

    public void setTapeSymbols(Set<Character> tapeSymbols) {
        if (tapeSymbols.isEmpty()) {
            tapeSymbols.add('B');
        }
        this.tapeSymbols = tapeSymbols;
        this.tapeSymbolsChangedPublisher.publish(new TapeSymbolsChangedEvent(tapeSymbols));
        if (!this.tapeSymbols.contains(this.blankSymbol)) {
            this.setBlankSymbol(this.tapeSymbols.iterator().next());
        }
    }

    public void setBlankSymbol(Character blankSymbol) {
        this.blankSymbol = blankSymbol;
        this.blankSymbolChangedPublisher.publish(new BlankSymbolChangedEvent(blankSymbol));
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
        this.initialStateChangedPublisher.publish(new InitialStateChangedEvent(initialState));
    }

    public Set<Character> getInputSymbols() {
        Set<Character> inputSymbols = new HashSet<>();
        for (char c : this.initialState.toCharArray()) {
            inputSymbols.add(c);
        }
        return inputSymbols;
    }
}
