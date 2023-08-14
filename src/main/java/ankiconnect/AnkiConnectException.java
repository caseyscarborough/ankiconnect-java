package ankiconnect;

public class AnkiConnectException extends RuntimeException {

    public AnkiConnectException(String message) {
        super(message);
    }

    public AnkiConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
