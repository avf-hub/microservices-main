package store.laptop.shipping.exception;

public class ShippingProcessingException extends BusinessException {
	public ShippingProcessingException(String message,
	                                   Long id) {
		super(message, id);
	}
}
