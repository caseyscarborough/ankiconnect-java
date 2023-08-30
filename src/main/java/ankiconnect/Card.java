package ankiconnect;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Card {
    @JsonProperty("note")
    private long noteId;
    private long cardId;
    private String modelName;
    private String deckName;
    private String question;
    private String answer;
    private String css;
    private int ord;
    private int reps;
    private int mod;
    private int lapses;
    private int due;
    private int left;
    private int interval;
    private int factor;
    private int fieldOrder;
    private Map<String, CardField> fields;
    @JsonDeserialize(using = CardTypeDeserializer.class)
    private CardType type;
    @JsonDeserialize(using = CardQueueDeserializer.class)
    private CardQueue queue;

    public CardState getCardState() {
        switch (queue) {
            case SUSPENDED:
                return CardState.SUSPENDED;
            case SIBLING_BURIED:
            case MANUALLY_BURIED:
                return CardState.BURIED;
            default:
                break;
        }
        switch (type) {
            case NEW:
                return CardState.NEW;
            case LEARN:
                return CardState.LEARNING;
            case RELEARN:
                return CardState.RELEARNING;
            case REVIEW:
                return interval < 21 ? CardState.YOUNG : CardState.MATURE;
            default:
                break;
        }

        return CardState.UNKNOWN;
    }
}
