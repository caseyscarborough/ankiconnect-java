package ankiconnect;

class CardQueueDeserializer extends BaseTypeDeserializer<CardQueue> {

    @Override
    CardQueue[] getTypes() {
        return CardQueue.values();
    }
}
