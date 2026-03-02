

package store.laptop.shoppingcart.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.laptop.shoppingcart.domain.exception.ShoppingCartException;
import store.laptop.shoppingcart.domain.model.ShoppingCart;
import store.laptop.shoppingcart.domain.model.ShoppingCartItem;
import store.laptop.shoppingcart.domain.model.ShoppingCartRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
public class ShoppingCartHandlingService implements ShoppingCartHandler {
	private final ShoppingCartRepository cartRepository;

	@Autowired
	public ShoppingCartHandlingService(ShoppingCartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	@Override
	public ShoppingCart getShoppingCart(Long cartId) {
		try {
			return cartRepository.findById(cartId).orElseThrow();
		} catch (NoSuchElementException ex) {
			throw new ShoppingCartException("Cannot find a shopping cart with id: " + cartId + ".",
				cartId, ex);
		}
	}

	@Override
	public ShoppingCart addItemOrIncrementCountIfPresent(Long cartId, ShoppingCartItem newItem) {
		try {
			ShoppingCart cart = cartRepository.findById(cartId).orElseThrow();
			cart.getItems().stream()
				.filter(item -> item.getProductId().equals(newItem.getProductId()))
				.findAny()
				.ifPresentOrElse(item -> item.setQty(item.getQty() + newItem.getQty()),
					() -> cart.getItems().add(newItem));
			return cartRepository.save(cart);
		} catch (NoSuchElementException ex) {
			throw new ShoppingCartException("Cannot add new cart item with cartId: " + cartId + ".",
				cartId, ex);
		}
	}

	@Override
	public ShoppingCart changeItemCount(Long cartId, Long productId, Long newCount) {
		try {
			ShoppingCart cart = cartRepository.findById(cartId).orElseThrow();
			cart.getItems().stream()
				.filter(item -> item.getProductId().equals(productId))
				.forEach(item -> item.setQty(newCount));
			return cartRepository.save(cart);
		} catch (NoSuchElementException ex) {
			throw new ShoppingCartException("Cannot change item count with cartId: " + cartId +
				" and productId: " + productId + ".",
				cartId, ex);
		}
	}

	@Override
	public ShoppingCart removeItem(Long cartId, Long productId) {
		try {
			ShoppingCart cart = cartRepository.findById(cartId).orElseThrow();
			cart.getItems().removeIf(item -> item.getProductId().equals(productId));
			return cartRepository.save(cart);
		} catch (NoSuchElementException ex) {
			throw new ShoppingCartException("Cannot remove item with cartId: " + cartId +
				" and productId: " + productId + ".",
				cartId, ex);
		}
	}

	@Override
	public void cleanupCart(Long cartId) {
		try {
			cartRepository.deleteById(cartId);
		} catch (DataAccessException ex) {
			throw new ShoppingCartException("Cannot cleanup a cart with id: " + cartId + ".",
				cartId, ex);
		}
	}
}
