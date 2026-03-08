

package store.laptop.ordering.shared.model.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemReservationRequest {

	private Long inventoryItemId;
	private Long requiredQty;
}
