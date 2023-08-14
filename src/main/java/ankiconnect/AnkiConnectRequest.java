package ankiconnect;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
class AnkiConnectRequest<T> {

    private String action;
    private int version;
    private Map<String, T> params;
    private String key;
}
