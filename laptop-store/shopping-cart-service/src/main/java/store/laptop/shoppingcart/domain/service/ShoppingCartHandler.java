

package store.laptop.shoppingcart.domain.service;

import store.laptop.shoppingcart.domain.model.ShoppingCart;
import store.laptop.shoppingcart.domain.model.ShoppingCartItem;

public interface ShoppingCartHandler {

	ShoppingCart getShoppingCart(Long cartId);

	ShoppingCart addItemOrIncrementCountIfPresent(Long cartId, ShoppingCartItem newItem);

	ShoppingCart changeItemCount(Long cartId, Long productId, Long newCount);

	ShoppingCart removeItem(Long cartId, Long productId);

	void cleanupCart(Long cartId);
}
