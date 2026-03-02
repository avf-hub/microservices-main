

package store.laptop.payment.exception;

public class PaymentValidationException extends BusinessException {

	public PaymentValidationException(String message, Long paymentId) {
		super(message, paymentId);
	}
}
