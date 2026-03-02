

package store.laptop.ordertracking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.ordertracking.exception.BusinessException;
import store.laptop.ordertracking.shared.model.web.dto.OrderTrackingErrorResponse;

@ControllerAdvice
public class OrderTrackingControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<OrderTrackingErrorResponse> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(
			OrderTrackingErrorResponse.builder()
				.orderId(ex.getOrderId())
				.message(ex.getMessage())
				.causeMessage(ex.getCause().getMessage())
				.build(), HttpStatus.BAD_REQUEST);
	}
}
