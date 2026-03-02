

package store.laptop.ordering.shared.model.web.dto;

import lombok.Data;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlaceOrderRequest {

	Long customerId;

	@Singular
	Set<OrderItemInfo> items = new HashSet<>();
}
