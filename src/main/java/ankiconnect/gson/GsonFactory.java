package ankiconnect.gson;

import ankiconnect.model.CardQueue;
import ankiconnect.model.CardType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

public class GsonFactory {

    public Gson getInstance() {
        return new GsonBuilder()
            .registerTypeAdapter(CardType.class, new CardTypeDeserializer())
            .registerTypeAdapter(CardQueue.class, new CardQueueDeserializer())
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();
    }
}
