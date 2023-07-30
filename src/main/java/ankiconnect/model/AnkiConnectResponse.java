package ankiconnect.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnkiConnectResponse<T> {
    private T result;
    private String error;
}
