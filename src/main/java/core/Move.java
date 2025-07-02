package core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the possible moves of a Turing machine head.
 */
@Getter
@RequiredArgsConstructor
public enum Move {
    LEFT('L'),
    RIGHT('R'),
    NONE('N');

    private final Character symbol;
}
