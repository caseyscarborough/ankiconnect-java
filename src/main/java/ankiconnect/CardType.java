package ankiconnect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType implements Indexable {
    NEW(0),
    LEARN(1),
    REVIEW(2),
    RELEARN(3);

    private final int index;
}
