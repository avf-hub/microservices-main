

package store.laptop.payment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private Long paymentId;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Long paymentId) {
		super(message);
		this.paymentId = paymentId;
	}

	public BusinessException(String message, Long paymentId, Throwable cause) {
		super(message, cause);
		this.paymentId = paymentId;
	}
}
