

package store.laptop.ordering.shared.model.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemInfo {
	Long productId;
	Long qty;
	BigDecimal cost;
}
