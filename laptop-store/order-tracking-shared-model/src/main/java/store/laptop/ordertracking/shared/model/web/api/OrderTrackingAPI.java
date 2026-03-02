package store.laptop.ordertracking.shared.model.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.laptop.ordertracking.shared.model.web.dto.OrderTrackingInfo;

public interface OrderTrackingAPI {
	@GetMapping("{orderId}")
	OrderTrackingInfo getOrderStatus(@PathVariable Long orderId);
}
