

package store.laptop.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.inventory.domain.exception.BusinessException;
import store.laptop.inventory.domain.exception.InventoryOperationException;
import store.laptop.inventory.shared.model.web.dto.ErrorResponse;

@ControllerAdvice
public class InventoryControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class, InventoryOperationException.class})
	public ResponseEntity<ErrorResponse> handleBusinessException(InventoryOperationException ex) {
		return new ResponseEntity<>(ErrorResponse.builder()
			.inventoryItemId(ex.getInventoryItemId())
			.message(ex.getMessage())
			.build(), HttpStatus.BAD_REQUEST);
	}
}
