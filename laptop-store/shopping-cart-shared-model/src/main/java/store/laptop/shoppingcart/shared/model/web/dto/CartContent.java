

package store.laptop.shoppingcart.shared.model.web.dto;

import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartContent {
	private Long cartId;

	@Singular
	private List<CartItem> items = new ArrayList<>();
}
