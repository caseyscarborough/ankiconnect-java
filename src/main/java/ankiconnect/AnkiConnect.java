package ankiconnect;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AnkiConnect {

    private final AnkiConnectHttpClient client;

    public AnkiConnect() {
        this("localhost", 8765);
    }

    public AnkiConnect(String bindAddress, Integer bindPort) {
        this(bindAddress, bindPort, null);
    }

    public AnkiConnect(String bindAddress, Integer bindPort, String apiKey) {
        this(new AnkiConnectHttpClient(bindAddress, bindPort, apiKey));
    }

    AnkiConnect(AnkiConnectHttpClient client) {
        this.client = client;
    }

    public List<Long> findCards(String query) {
        log.info("Finding cards in Anki with query: {}", query);
        return request("findCards", params("query", query), new TypeReference<AnkiConnectResponse<List<Long>>>() {}).getResult();
    }

    public List<Card> cardsInfo(List<Long> ids) {
        log.info("Finding card info from Anki for ids: {}", ids);
        return request("cardsInfo", params("cards", ids), new TypeReference<AnkiConnectResponse<List<Card>>>() {}).getResult();
    }

    public List<Review> getReviewsOfCard(Long id) {
        final Map<Long, List<Review>> reviews = getReviewsOfCards(Collections.singletonList(id));
        return reviews.get(id);
    }

    public Map<Long, List<Review>> getReviewsOfCards(List<Long> ids) {
        log.info("Finding reviews from Anki for ids: {}", ids);
        return request("getReviewsOfCards", params("cards", ids), new TypeReference<AnkiConnectResponse<Map<String, List<Review>>>>() {}).getResult()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> Long.parseLong(e.getKey()), Map.Entry::getValue));
    }

    private <T> Map<String, T> params(String key, T value) {
        Map<String, T> params = new HashMap<>();
        params.put(key, value);
        return params;
    }

    private <P, R> AnkiConnectResponse<R> request(String action, Map<String, P> params, TypeReference<AnkiConnectResponse<R>> token) {
        final AnkiConnectResponse<R> response = client.request(action, params, token);
        if (response.getError() != null) {
            throw new AnkiConnectException("An error was returning from the AnkiConnect API: " + response.getError());
        }
        return response;
    }
}
