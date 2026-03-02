

package store.laptop.shoppingcart.domain.exception;

public class ShoppingCartException extends BusinessException {

	public ShoppingCartException() {
		super();
	}

	public ShoppingCartException(String message, Long cartId) {
		super(message, cartId);
	}

	public ShoppingCartException(String message, Long cartId, Throwable cause) {
		super(message, cartId, cause);
	}
}
