

package store.laptop.ordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.ordering.controller.dto.mapping.OrderDataMapper;
import store.laptop.ordering.controller.dto.mapping.OrderInfoMapper;
import store.laptop.ordering.domain.model.Order;
import store.laptop.ordering.domain.service.OrderMessagingPublisher;
import store.laptop.ordering.domain.service.OrderProcessingService;
import store.laptop.ordering.shared.model.web.api.OrderingAPI;
import store.laptop.ordering.shared.model.web.dto.OrderData;
import store.laptop.ordering.shared.model.web.dto.OrderInfo;
import store.laptop.ordering.shared.model.web.dto.PlaceOrderRequest;

@RestController
@RequestMapping("orders")
public class OrderController implements OrderingAPI {
	private final OrderProcessingService processingService;
	private final OrderInfoMapper orderInfoMapper;
	private final OrderDataMapper orderDataMapper;
	private final OrderMessagingPublisher orderMessagingPublisher;

	@Autowired
	public OrderController(OrderProcessingService processingService,
	                       OrderDataMapper orderDataMapper,
	                       OrderInfoMapper orderInfoMapper,
						   OrderMessagingPublisher orderMessagingPublisher) {
		this.processingService = processingService;
		this.orderInfoMapper = orderInfoMapper;
		this.orderDataMapper = orderDataMapper;
		this.orderMessagingPublisher = orderMessagingPublisher;
	}

	@Override
	public OrderInfo placeOrder(PlaceOrderRequest orderRequest) {
		Order order = processingService.placeOrder(orderRequest);
		orderMessagingPublisher.publish(orderDataMapper.map(order));
		return orderInfoMapper.map(order);
	}

	@Override
	public OrderInfo verifyOrder(Long orderId) {
		Order order = processingService.verifyOrder(orderId);
		return orderInfoMapper.map(order);
	}

	@Override
	public OrderInfo getOrderStatus(Long orderId) {
		Order order = processingService.checkOrderState(orderId);
		return orderInfoMapper.map(order);
	}

	@Override
	public OrderData getOrderData(Long orderId) {
		Order order = processingService.getOrder(orderId);
		return orderDataMapper.map(order);
	}
}
