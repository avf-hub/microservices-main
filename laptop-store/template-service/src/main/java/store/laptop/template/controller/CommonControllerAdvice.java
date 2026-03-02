

package store.laptop.template.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import store.laptop.template.domain.exception.BusinessException;
import store.laptop.template.shared.model.web.dto.ErrorResponse;

@ControllerAdvice
public class CommonControllerAdvice {
	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(ErrorResponse.builder()
			.stubId(ex.getStubId())
			.message(ex.getMessage())
			.build(), HttpStatus.BAD_REQUEST);
	}
}
