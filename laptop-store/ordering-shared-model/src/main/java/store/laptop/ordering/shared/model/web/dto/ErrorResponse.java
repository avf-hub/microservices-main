

package store.laptop.ordering.shared.model.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {

	Long orderId;
	String message;
}
