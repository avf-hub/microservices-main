

package store.laptop.inventory.shared.model.web.dto;

import lombok.Data;

@Data
public class ItemReservationRequest {

	private Long inventoryItemId;
	private Long requiredQty;
}
