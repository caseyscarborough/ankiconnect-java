package ankiconnect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
class AnkiConnectHttpClient {

    private static final int VERSION = 6;

    private final String host;
    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public AnkiConnectHttpClient(String bindAddress, Integer bindPort, String apiKey) {
        this.host = "http://" + bindAddress + ":" + bindPort;
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
        this.mapper = JacksonConfiguration.getObjectMapper();
    }

    public <P, R> AnkiConnectResponse<R> request(AnkiConnectRequest<P> body, TypeReference<AnkiConnectResponse<R>> token) {
        Response response = null;
        try {
            log.debug("Making {} request to AnkiConnect at {}", body.getAction(), host);
            Request request = new Request.Builder()
                .url(host)
                .post(RequestBody.create(mapper.writeValueAsString(body), MediaType.parse("application/json")))
                .build();
            response = client.newCall(request).execute();
            if (response.body() == null) {
                throw new AnkiConnectException("Response body was null");
            }
            return mapper.readValue(response.body().string(), token);
        } catch (IOException e) {
            throw new AnkiConnectException("Could not make request to the AnkiConnect API", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public <P, R> AnkiConnectResponse<R> request(String action, Map<String, P> params, TypeReference<AnkiConnectResponse<R>> token) {
        AnkiConnectRequest<P> body = new AnkiConnectRequest<>();
        body.setAction(action);
        body.setKey(apiKey);
        body.setVersion(VERSION);
        body.setParams(params);
        return request(body, token);
    }
}
