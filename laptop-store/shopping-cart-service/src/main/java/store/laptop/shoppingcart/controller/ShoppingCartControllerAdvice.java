

package store.laptop.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.laptop.shoppingcart.domain.exception.BusinessException;
import store.laptop.shoppingcart.domain.exception.ShoppingCartException;
import store.laptop.shoppingcart.shared.model.web.dto.ErrorResponse;

@ControllerAdvice
public class ShoppingCartControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class, ShoppingCartException.class})
	public ResponseEntity<ErrorResponse> handleBusinessException(ShoppingCartException ex) {
		return new ResponseEntity<>(ErrorResponse.builder()
			.cartId(ex.getCartId())
			.message(ex.getMessage())
			.build(), HttpStatus.BAD_REQUEST);
	}
}
