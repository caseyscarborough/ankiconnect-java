package ankiconnect;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

class CardQueueDeserializer extends JsonDeserializer<CardQueue> {

    @Override
    public CardQueue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final int index = p.getIntValue();
        return Arrays.stream(CardQueue.values())
            .filter(queue -> queue.getIndex() == index)
            .findFirst()
            .orElse(null);
    }
}
