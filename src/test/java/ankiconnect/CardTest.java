package ankiconnect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @Test
    void testGetState() {
        Card card = new Card();
        card.setQueue(CardQueue.SUSPENDED);
        assertEquals(CardState.SUSPENDED, card.getCardState());
        card.setQueue(CardQueue.SIBLING_BURIED);
        assertEquals(CardState.BURIED, card.getCardState());
        card.setQueue(CardQueue.MANUALLY_BURIED);
        assertEquals(CardState.BURIED, card.getCardState());
        card.setQueue(null);
        card.setType(CardType.LEARN);
        assertEquals(CardState.LEARNING, card.getCardState());
        card.setType(CardType.NEW);
        assertEquals(CardState.NEW, card.getCardState());
        card.setType(CardType.RELEARN);
        assertEquals(CardState.RELEARNING, card.getCardState());
        card.setType(CardType.REVIEW);
        card.setInterval(1);
        assertEquals(CardState.YOUNG, card.getCardState());
        card.setInterval(21);
        assertEquals(CardState.MATURE, card.getCardState());
        card.setType(null);
        assertEquals(CardState.UNKNOWN, card.getCardState());
    }
}
