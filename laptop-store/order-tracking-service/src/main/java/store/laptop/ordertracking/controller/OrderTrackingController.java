package store.laptop.ordertracking.controller;

import org.springframework.web.bind.annotation.RestController;
import store.laptop.ordering.shared.model.web.dto.OrderInfo;
import store.laptop.ordering.shared.model.web.dto.OrderStatus;
import store.laptop.ordertracking.client.OrderingServiceClient;
import store.laptop.ordertracking.client.PaymentServiceClient;
import store.laptop.ordertracking.client.ShippingServiceClient;
import store.laptop.ordertracking.exception.OrderTrackingException;
import store.laptop.ordertracking.shared.model.web.api.OrderTrackingAPI;
import store.laptop.ordertracking.shared.model.web.dto.OrderTrackingInfo;
import store.laptop.payment.shared.model.web.dto.PaymentStatus;
import store.laptop.shipping.shared.model.web.dto.ShippingStatus;

@RestController
public class OrderTrackingController implements OrderTrackingAPI {
    OrderingServiceClient orderingServiceClient;
    PaymentServiceClient paymentServiceClient;
    ShippingServiceClient shippingServiceClient;

    public OrderTrackingController(OrderingServiceClient orderingServiceClient,
                                   PaymentServiceClient paymentServiceClient,
                                   ShippingServiceClient shippingServiceClient
    ) {
        this.orderingServiceClient = orderingServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.shippingServiceClient = shippingServiceClient;
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

            ShippingStatus shippingStatus = shippingServiceClient.getShippingStatus(orderId);

            return OrderTrackingInfo.builder()
                    .orderStatus(orderInfo.getStatus())
                    .paymentStatus(paymentStatus)
                    .shippingStatus(shippingStatus)
                    .build();

        } catch (Exception e) {
            throw new OrderTrackingException("Error in tracking order", orderId, e);
        }
    }
}
