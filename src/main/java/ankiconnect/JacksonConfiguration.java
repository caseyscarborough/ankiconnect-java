package ankiconnect;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class JacksonConfiguration {

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(module());
    }

    private static SimpleModule module() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CardQueue.class, new IndexableDeserializer<>(CardQueue.values()));
        module.addDeserializer(CardType.class, new IndexableDeserializer<>(CardType.values()));
        module.addDeserializer(ReviewType.class, new IndexableDeserializer<>(ReviewType.values()));
        module.addSerializer(CardQueue.class, new IndexableSerializer<>());
        module.addSerializer(CardType.class, new IndexableSerializer<>());
        module.addSerializer(ReviewType.class, new IndexableSerializer<>());
        return module;
    }

    @RequiredArgsConstructor
    private static class IndexableDeserializer<T extends Enum<T> & Indexable> extends JsonDeserializer<T> {
        private final T[] values;

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            final int index = p.getIntValue();
            return Arrays.stream(values)
                .filter(queue -> queue.getIndex() == index)
                .findFirst()
                .orElse(null);
        }
    }

    @RequiredArgsConstructor
    private static class IndexableSerializer<T extends Enum<T> & Indexable> extends JsonSerializer<T> {
        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(value.getIndex());
        }
    }
}
