

package store.laptop.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.payment.exception.BusinessException;
import store.laptop.payment.shared.model.web.dto.PaymentErrorResponse;

@ControllerAdvice
public class PaymentControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<PaymentErrorResponse> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(
			PaymentErrorResponse.builder()
				.paymentId(ex.getPaymentId())
				.message(ex.getMessage())
				.build(), HttpStatus.BAD_REQUEST);
	}
}
