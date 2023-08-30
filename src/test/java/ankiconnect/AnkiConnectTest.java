package ankiconnect;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests were ran with a new Anki profile with the following deck:
// https://ankiweb.net/shared/info/2009196675
//
class AnkiConnectTest {

    private final AnkiConnect connect = new AnkiConnect(new TestHttpClient());

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
    }

    @Test
    void testReviewsForCards() {
        final Long id = 1354715263726L;
        final Map<Long, List<Review>> map = connect.getReviewsOfCards(Collections.singletonList(id));
        assertEquals(1, map.size());
        final List<Review> reviews = map.get(id);
        assertEquals(2, reviews.size());
    }
}
