package observer.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BlankSymbolChangedEvent {
    private final Character symbol;
}
