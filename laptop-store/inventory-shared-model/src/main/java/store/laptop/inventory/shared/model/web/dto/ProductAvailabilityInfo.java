

package store.laptop.inventory.shared.model.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAvailabilityInfo {

	private Long inventoryId;
	private Long productId;
	private Long availableQty;
	private BigDecimal unitCost;
}
