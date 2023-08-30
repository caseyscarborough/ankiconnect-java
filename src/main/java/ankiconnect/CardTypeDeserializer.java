package ankiconnect;

class CardTypeDeserializer extends BaseTypeDeserializer<CardType> {

    @Override
    CardType[] getTypes() {
        return CardType.values();
    }
}
