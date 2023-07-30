package ankiconnect.gson;

import ankiconnect.model.CardType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class CardTypeDeserializer implements JsonDeserializer<CardType> {
    @Override
    public CardType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final int index = json.getAsInt();

        for (CardType type : CardType.values()) {
            if (type.getIndex() == index) {
                return type;
            }
        }

        return null;
    }
}
