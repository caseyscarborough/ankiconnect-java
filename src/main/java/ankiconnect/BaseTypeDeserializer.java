package ankiconnect;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
abstract class BaseTypeDeserializer<T extends Enum<T> & Indexable> extends JsonDeserializer<T> {

    abstract T[] getTypes();

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        final int index = p.getIntValue();
        return Arrays.stream(getTypes())
            .filter(queue -> queue.getIndex() == index)
            .findFirst()
            .orElse(null);
    }
}
