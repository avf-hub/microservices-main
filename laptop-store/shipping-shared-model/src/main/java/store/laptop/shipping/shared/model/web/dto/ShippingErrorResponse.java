

package store.laptop.shipping.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingErrorResponse {
	private Long orderId;
	private String message;
}
