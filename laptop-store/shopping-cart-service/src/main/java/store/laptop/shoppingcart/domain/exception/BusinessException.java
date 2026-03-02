

package store.laptop.shoppingcart.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

	private Long cartId;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Long cartId) {
		super(message);
		this.cartId = cartId;
	}

	public BusinessException(String message, Long cartId, Throwable cause) {
		super(message, cause);
		this.cartId = cartId;
	}
}
