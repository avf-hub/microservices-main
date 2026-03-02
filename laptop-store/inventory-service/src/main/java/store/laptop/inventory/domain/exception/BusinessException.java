

package store.laptop.inventory.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private Long inventoryItemId;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Long inventoryItemId) {
		super(message);
		this.inventoryItemId = inventoryItemId;
	}

	public BusinessException(String message, Long inventoryItemId, Throwable cause) {
		super(message, cause);
		this.inventoryItemId = inventoryItemId;
	}
}
