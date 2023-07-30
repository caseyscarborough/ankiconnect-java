package ankiconnect.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardField {
	private String value;
	private int order;

	public String toString() {
		return value;
	}
}
