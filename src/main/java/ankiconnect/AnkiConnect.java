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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AnkiConnect {

    private static final int VERSION = 6;

    private final String bindAddress;
    private final Integer bindPort;
    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public AnkiConnect(String bindAddress, Integer bindPort) {
        this(bindAddress, bindPort, null);
    }

    public AnkiConnect(String bindAddress, Integer bindPort, String apiKey) {
        this.bindAddress = bindAddress;
        this.bindPort = bindPort;
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
        this.mapper = new ObjectMapper();
    }

    public List<Long> findCards(String query) {
        log.info("Finding cards in Anki with query: {}", query);
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        return request("findCards", params, new TypeReference<AnkiConnectResponse<List<Long>>>() {});
    }

    public List<Card> cardsInfo(List<Long> ids) {
        log.info("Finding card info from Anki for ids: {}", ids);
        Map<String, List<Long>> params = new HashMap<>();
        params.put("cards", ids);
        return request("cardsInfo", params, new TypeReference<AnkiConnectResponse<List<Card>>>() {});
    }

    private <P, R> R request(String action, Map<String, P> params, TypeReference<AnkiConnectResponse<R>> token) {
        AnkiConnectRequest<P> body = new AnkiConnectRequest<>();
        body.setAction(action);
        body.setKey(apiKey);
        body.setVersion(VERSION);
        body.setParams(params);

        Response response = null;
        try {
            Request request = new Request.Builder()
                .url("http://" + bindAddress + ":" + bindPort)
                .post(RequestBody.create(mapper.writeValueAsString(body), MediaType.parse("application/json")))
                .build();
            response = client.newCall(request).execute();
            if (response.body() == null) {
                throw new AnkiConnectException("Response body was null");
            }
            AnkiConnectResponse<R> output = mapper.readValue(response.body().string(), token);
            if (output.getError() != null) {
                throw new AnkiConnectException("An error was returning from the AnkiConnect API: " + output.getError());
            }
            return output.getResult();
        } catch (IOException e) {
            throw new AnkiConnectException("Could not make request to the AnkiConnect API", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
