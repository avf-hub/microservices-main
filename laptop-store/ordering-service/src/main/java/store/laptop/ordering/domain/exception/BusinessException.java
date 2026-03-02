

package store.laptop.ordering.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

	private Long orderId;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Long orderId) {
		super(message);
		this.orderId = orderId;
	}

	public BusinessException(String message, Long orderId, Throwable cause) {
		super(message, cause);
		this.orderId = orderId;
	}
}
