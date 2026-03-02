

package store.laptop.ordering.shared.model.web.dto;

import lombok.Data;
import lombok.Singular;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderData {
	Long orderId;
	Long customerId;

	@Singular
	Set<OrderItemInfo> items = new HashSet<>();

	public BigDecimal getCost() {
		return items.stream()
				.map(OrderItemInfo::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
