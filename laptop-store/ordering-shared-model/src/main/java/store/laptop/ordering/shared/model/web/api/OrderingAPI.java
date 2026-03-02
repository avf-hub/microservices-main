package store.laptop.ordering.shared.model.web.api;

import org.springframework.web.bind.annotation.*;
import store.laptop.ordering.shared.model.web.dto.OrderData;
import store.laptop.ordering.shared.model.web.dto.OrderInfo;
import store.laptop.ordering.shared.model.web.dto.PlaceOrderRequest;

public interface OrderingAPI {
	@PostMapping
	OrderInfo placeOrder(@RequestBody PlaceOrderRequest orderRequest);

	@PutMapping("{orderId}/verification")
	OrderInfo verifyOrder(@PathVariable("orderId") Long orderId);

	@RequestMapping("{orderId}/status")
	OrderInfo getOrderStatus(@PathVariable("orderId") Long orderId);

	@RequestMapping("{orderId}/data")
	OrderData getOrderData(@PathVariable("orderId") Long orderId);
}
