package ankiconnect.gson;

import ankiconnect.model.CardQueue;
import ankiconnect.model.CardType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class CardQueueDeserializer implements JsonDeserializer<CardQueue> {
    @Override
    public CardQueue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final int index = json.getAsInt();

        for (CardQueue queue : CardQueue.values()) {
            if (queue.getIndex() == index) {
                return queue;
            }
        }

        return null;
    }
}
