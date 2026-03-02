

package store.laptop.ordertracking.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderTrackingErrorResponse {
	private Long orderId;
	private String message;
	private String causeMessage;
}
