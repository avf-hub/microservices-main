

package store.laptop.shoppingcart.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Table(name = "shopping_cart_items")
@Data
public class ShoppingCartItem {

	private Long productId;
	private Long qty;
}
