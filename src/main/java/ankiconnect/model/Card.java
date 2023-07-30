package ankiconnect.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Card {
    private String modelName;
    private String deckName;

    @SerializedName("note")
    private long noteId;
    private long cardId;
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
    private CardType type;
    private CardQueue queue;
    private Map<String, CardField> fields;
}
