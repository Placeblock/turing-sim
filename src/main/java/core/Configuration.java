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

    public Set<Character> getInputSymbols() {
        Set<Character> inputSymbols = new HashSet<>();
        for (char c : this.initialState.toCharArray()) {
            inputSymbols.add(c);
        }
        return inputSymbols;
    }
}
