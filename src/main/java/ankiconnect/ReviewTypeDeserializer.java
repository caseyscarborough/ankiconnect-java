package ankiconnect;

class ReviewTypeDeserializer extends BaseTypeDeserializer<ReviewType> {

    @Override
    ReviewType[] getTypes() {
        return ReviewType.values();
    }
}
