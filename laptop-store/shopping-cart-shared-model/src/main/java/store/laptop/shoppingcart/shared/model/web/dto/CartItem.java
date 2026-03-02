

package store.laptop.shoppingcart.shared.model.web.dto;

import lombok.Data;

@Data
public class CartItem {

	private Long itemId;
	private Long productId;
	private Long qty;
}
