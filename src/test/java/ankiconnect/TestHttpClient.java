package ankiconnect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
class TestHttpClient extends AnkiConnectHttpClient {

    private final ObjectMapper mapper = JacksonConfiguration.getObjectMapper();

    public TestHttpClient() {
        super("localhost", 8765, null);
    }

    @Override
    public <P, R> AnkiConnectResponse<R> request(AnkiConnectRequest<P> body, TypeReference<AnkiConnectResponse<R>> token) {
        try {
            final String s = mapper.writeValueAsString(body);
            final String hash = DigestUtils.md5Hex(s);
            final String relativePath = "src/test/resources/json/" + hash + ".json";
            final Path path = Paths.get(relativePath);
            if (Files.exists(path)) {
                log.info("Reading cached response from {}", relativePath);
                return mapper.readValue(Files.readAllBytes(path), token);
            }
            final AnkiConnectResponse<R> response = super.request(body, token);
            final File file = path.toFile();
            file.getParentFile().mkdirs();
            log.info("Caching response to {}", relativePath);
            mapper.writeValue(file, response);
            return response;
        } catch (Exception e) {
            throw new AnkiConnectException("Could not make request to the AnkiConnect API", e);
        }
    }
}
