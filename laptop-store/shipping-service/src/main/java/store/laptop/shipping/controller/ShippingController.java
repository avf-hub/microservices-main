package store.laptop.shipping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.shipping.service.ShippingService;
import store.laptop.shipping.shared.model.web.api.ShippingAPI;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

@RestController
@RequestMapping("/shipping")
public class ShippingController implements ShippingAPI {
	private final ShippingService shippingService;

	public ShippingController(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	@Override
	public Long startShipping(Long orderId) {
		return shippingService.startShipping(orderId);
	}

	@Override
	public Boolean clearShipping(Long orderId) {
		return shippingService.clearShipping(orderId);
	}

	@Override
	public ShippingStatus getShippingStatus(Long orderId) {
		return shippingService.getStatusByOrderId(orderId);
	}
}
