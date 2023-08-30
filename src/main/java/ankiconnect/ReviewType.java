package ankiconnect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewType implements Indexable {
    LEARNING(0),
    REVIEW(1),
    RELEARNING(2),
    FILTERED(3),
    MANUAL(4);

    private final int index;
}
