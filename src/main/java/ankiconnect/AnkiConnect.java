package ankiconnect;

public class AnkiConnect {

    private final String bindAddress;
    private final Integer bindPort;
    private String apiKey;

    public AnkiConnect(String bindAddress, Integer bindPort) {
        this(bindAddress, bindPort, null);
    }

    public AnkiConnect(String bindAddress, Integer bindPort, String apiKey) {
        this.bindAddress = bindAddress;
        this.bindPort = bindPort;
        this.apiKey = apiKey;
    }
}
