

package store.laptop.catalog.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private Long productId;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Long productId) {
		super(message);
		this.productId = productId;
	}

	public BusinessException(String message, Long productId, Throwable cause) {
		super(message, cause);
		this.productId = productId;
	}
}
