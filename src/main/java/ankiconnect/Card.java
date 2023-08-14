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
}