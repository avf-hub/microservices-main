

package store.laptop.shoppingcart.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private Long cartId;
	private String message;
}
