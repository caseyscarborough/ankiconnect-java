package ankiconnect;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests were ran with a new Anki profile with the following deck:
// https://ankiweb.net/shared/info/2009196675
//
class AnkiConnectTest {

    private final AnkiConnectHttpClient client = new TestHttpClient();
    private final AnkiConnect connect = new AnkiConnect(client);

    @Test
    void testFindCards() {
        final List<Long> cards = connect.findCards("deck:*");
        assertFalse(cards.isEmpty());
        assertTrue(cards.contains(1354715263726L));
    }

    @Test
    void testFindCardsWithEmptyQuery() {
        final List<Long> cards = connect.findCards("");
        assertFalse(cards.isEmpty());
        assertTrue(cards.contains(1354715263726L));
    }

    @Test
    void testFindCardsWithNullQuery() {
        final List<Long> cards = connect.findCards(null);
        assertTrue(cards.isEmpty());
    }

    @Test
    void testCardsInfo() {
        final List<Card> cards = connect.cardsInfo(Collections.singletonList(1354715263726L));
        assertEquals(1, cards.size());

        final Card card = cards.get(0);
        assertEquals(1354715263726L, card.getCardId());
        assertNotNull(card.getQuestion());
        assertNotNull(card.getAnswer());
        assertNotNull(card.getCss());
        assertNotNull(card.getModelName());
        assertNotNull(card.getDeckName());
        assertNotNull(card.getQueue());
        assertNotNull(card.getType());
        final Map<String, CardField> fields = card.getFields();
        assertFalse(fields.isEmpty());
        for (Map.Entry<String, CardField> entry : fields.entrySet()) {
            final CardField field = entry.getValue();
            assertNotNull(field);
            assertNotNull(field.getValue());
            assertTrue(field.getOrder() >= 0);
        }
    }

    @Test
    void testReviewsForCard() {
        final Long id = 1354715263726L;
        final List<Review> reviews = connect.getReviewsOfCard(id);
        assertEquals(2, reviews.size());
        for (Review review : reviews) {
            assertNotNull(review);
            assertNotNull(review.getType());
            assertTrue(review.getTime() > 0);
            assertTrue(review.getEase() >= 1 && review.getEase() <= 4);
            assertTrue(review.getTime() < System.currentTimeMillis());
        }
    }

    @Test
    void testReviewsForCards() {
        final List<Long> ids = Arrays.asList(1354715263726L, 1354715369324L, 1354715369325L);
        final Map<Long, List<Review>> map = connect.getReviewsOfCards(ids);
        assertEquals(ids.size(), map.size());
        for (Map.Entry<Long, List<Review>> entry : map.entrySet()) {
            assertEquals(2, entry.getValue().size());
        }
    }

    @Test
    void testError() {
        final AnkiConnectResponse<String> response = client.request("null", new HashMap<>(), new TypeReference<AnkiConnectResponse<String>>() {});
        assertEquals("unsupported action", response.getError());
    }
}
