

package store.laptop.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.shoppingcart.controller.dto.mapping.CartContentMapper;
import store.laptop.shoppingcart.domain.model.ShoppingCart;
import store.laptop.shoppingcart.domain.service.ShoppingCartHandler;
import store.laptop.shoppingcart.shared.model.web.dto.CartContent;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
	private final ShoppingCartHandler cartHandler;
	private final CartContentMapper cartContentMapper;

	@Autowired
	public ShoppingCartController(ShoppingCartHandler cartHandler,
	                              CartContentMapper cartContentMapper) {
		this.cartHandler = cartHandler;
		this.cartContentMapper = cartContentMapper;
	}

	@GetMapping
	public CartContent getCartContentById(Long cartId) {
		ShoppingCart cart = cartHandler.getShoppingCart(cartId);
		return cartContentMapper.map(cart);
	}

	// TODO add corresponding RESTful API for unused methods from ShoppingCartHandler
}
