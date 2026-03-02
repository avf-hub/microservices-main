

package store.laptop.ordering.domain.exception;

public class OrderProcessingException extends BusinessException {

	public OrderProcessingException() {
		super();
	}

	public OrderProcessingException(String message, Long orderId) {
		super(message, orderId);
	}

	public OrderProcessingException(String message, Long orderId, Throwable cause) {
		super(message, orderId, cause);
	}
}
