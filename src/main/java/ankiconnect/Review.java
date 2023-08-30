package ankiconnect;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private long id;
    private int usn;
    private int ease;
    private int ivl;
    private int lastIvl;
    private int factor;
    private int time;

    @JsonDeserialize(using = ReviewTypeDeserializer.class)
    private ReviewType type;
}
