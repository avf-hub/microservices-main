package store.laptop.accounting.exception;

public class InvoiceProcessingException extends BusinessException {
	public InvoiceProcessingException(String message,
	                                  Long id) {
		super(message, id);
	}
}
