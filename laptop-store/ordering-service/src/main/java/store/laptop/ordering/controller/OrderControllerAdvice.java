

package store.laptop.ordering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.ordering.domain.exception.BusinessException;
import store.laptop.ordering.domain.exception.OrderProcessingException;
import store.laptop.ordering.shared.model.web.dto.ErrorResponse;

@ControllerAdvice
public class OrderControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class, OrderProcessingException.class})
	public ResponseEntity<ErrorResponse> handleBusinessException(OrderProcessingException ex) {
		return new ResponseEntity<>(ErrorResponse.builder()
			.orderId(ex.getOrderId())
			.message(ex.getMessage())
			.build(), HttpStatus.BAD_REQUEST);
	}
}
