package core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Configuration {

    private String initialState;
    private Set<Character> tapeSymbols;
    private Character blankSymbol;

    public Set<Character> getInputSymbols() {
        Set<Character> inputSymbols = new HashSet<>();
        for (char c : this.initialState.toCharArray()) {
            inputSymbols.add(c);
        }
        return inputSymbols;
    }
}
