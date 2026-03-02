

package store.laptop.inventory.domain.exception;

public class InventoryOperationException extends BusinessException {

	public InventoryOperationException() {
		super();
	}

	public InventoryOperationException(String message, Long inventoryItemId) {
		super(message, inventoryItemId);
	}

	public InventoryOperationException(String message, Long inventoryItemId, Throwable cause) {
		super(message, inventoryItemId, cause);
	}
}
