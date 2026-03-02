

package store.laptop.shipping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.shipping.exception.BusinessException;
import store.laptop.shipping.shared.model.web.dto.ShippingErrorResponse;

@ControllerAdvice
public class ShippingControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<ShippingErrorResponse> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(
			ShippingErrorResponse.builder()
				.orderId(ex.getOrderId())
				.message(ex.getMessage())
				.build(), HttpStatus.BAD_REQUEST);
	}
}
