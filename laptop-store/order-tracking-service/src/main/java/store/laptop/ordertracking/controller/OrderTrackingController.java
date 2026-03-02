package store.laptop.ordertracking.controller;

import org.springframework.web.bind.annotation.RestController;
import store.laptop.ordering.shared.model.web.dto.OrderInfo;
import store.laptop.ordering.shared.model.web.dto.OrderStatus;
import store.laptop.ordertracking.client.OrderingServiceClient;
import store.laptop.ordertracking.client.PaymentServiceClient;
import store.laptop.ordertracking.exception.OrderTrackingException;
import store.laptop.ordertracking.shared.model.web.api.OrderTrackingAPI;
import store.laptop.ordertracking.shared.model.web.dto.OrderTrackingInfo;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;

@RestController
public class OrderTrackingController implements OrderTrackingAPI {
	OrderingServiceClient orderingServiceClient;
	PaymentServiceClient paymentServiceClient;

	public OrderTrackingController(OrderingServiceClient orderingServiceClient,
	                               PaymentServiceClient paymentServiceClient
	) {
		this.orderingServiceClient = orderingServiceClient;
		this.paymentServiceClient = paymentServiceClient;
	}

	public OrderTrackingInfo getOrderStatus(Long orderId) {
		try {
			OrderInfo orderInfo = orderingServiceClient
					.getOrderStatus(orderId);

			PaymentStatus paymentStatus = null;
			if (orderInfo.getStatus() == OrderStatus.CONFIRMED ||
			    orderInfo.getStatus() == OrderStatus.CANCELED
			) {
				paymentStatus = paymentServiceClient
						.getStatusByOrder(orderId);
			}

			return OrderTrackingInfo.builder()
				.orderStatus(orderInfo.getStatus())
				.paymentStatus(paymentStatus)
				.build();

		} catch (Exception e) {
			throw new OrderTrackingException("Error in tracking order", orderId, e);
		}
	}
}
