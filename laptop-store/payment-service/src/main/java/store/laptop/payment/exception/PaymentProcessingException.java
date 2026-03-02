

package store.laptop.payment.exception;

public class PaymentProcessingException extends BusinessException {


	public PaymentProcessingException(String message, Long paymentId) {
		super(message, paymentId);
	}

	public PaymentProcessingException(String message, Long paymentId, Throwable cause) {
		super(message, paymentId, cause);
	}
}
