

package store.laptop.ordertracking.exception;

public class OrderTrackingException extends BusinessException {

	public OrderTrackingException(String message, Long orderId) {
		super(message, orderId);
	}

	public OrderTrackingException(String message, Long orderId, Throwable cause) {
		super(message, orderId, cause);
	}
}
