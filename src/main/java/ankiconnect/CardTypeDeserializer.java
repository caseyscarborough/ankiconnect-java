package ankiconnect;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

class CardTypeDeserializer extends JsonDeserializer<CardType> {

    @Override
    public CardType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final int index = p.getIntValue();
        return Arrays.stream(CardType.values()).filter(type -> type.getIndex() == index).findFirst().orElse(null);
    }
}
