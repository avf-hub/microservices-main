package store.laptop.shipping.shared.model.web.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

public interface ShippingAPI {
	@PutMapping("{orderId}")
	Long startShipping(@PathVariable("orderId") Long orderId);

	@DeleteMapping("{orderId}")
	Boolean clearShipping(@PathVariable("orderId") Long orderId);

	@GetMapping("{orderId}/status")
	ShippingStatus getShippingStatus(@PathVariable("orderId") Long orderId);
}
