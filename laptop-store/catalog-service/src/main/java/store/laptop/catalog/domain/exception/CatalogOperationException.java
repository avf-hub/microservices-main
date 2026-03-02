

package store.laptop.catalog.domain.exception;

public class CatalogOperationException extends BusinessException {

	public CatalogOperationException() {
		super();
	}

	public CatalogOperationException(String message, Long productId) {
		super(message, productId);
	}

	public CatalogOperationException(String message, Long productId, Throwable cause) {
		super(message, productId, cause);
	}
}
