package ankiconnect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AnkiConnectResponse<T> {
    private T result;
    private String error;
}
