

package store.laptop.accounting.exception;

public class InvoiceValidationException extends BusinessException {

	public InvoiceValidationException(String message, Long paymentId) {
		super(message, paymentId);
	}

	public InvoiceValidationException(String message, Long paymentId, Throwable cause) {
		super(message, paymentId, cause);
	}
}
