package ankiconnect.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardQueue {
    NEW(0),
    LEARN(1),
    REVIEW(2),
    RELEARN(3),
    PREVIEW(4),
    SUSPENDED(-1),
    SIBLING_BURIED(-2),
    MANUALLY_BURIED(-3);

    private final int index;
}
