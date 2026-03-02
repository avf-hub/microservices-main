

package store.laptop.inventory.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private Long inventoryItemId;
	private String message;
}
