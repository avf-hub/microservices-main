

package store.laptop.shipping.exception;

public class ShippingValidationException extends BusinessException {

	public ShippingValidationException(String message, Long paymentId) {
		super(message, paymentId);
	}

	public ShippingValidationException(String message, Long paymentId, Throwable cause) {
		super(message, paymentId, cause);
	}
}
