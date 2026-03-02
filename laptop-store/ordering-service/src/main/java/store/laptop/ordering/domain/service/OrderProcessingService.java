

package store.laptop.ordering.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.laptop.ordering.controller.dto.mapping.PlaceOrderRequestMapper;
import store.laptop.ordering.domain.exception.OrderProcessingException;
import store.laptop.ordering.domain.model.Order;
import store.laptop.ordering.domain.model.OrderRepository;
import store.laptop.ordering.domain.model.OrderState;
import store.laptop.ordering.shared.model.web.dto.PlaceOrderRequest;

import java.util.NoSuchElementException;

@Service
@Transactional
public class OrderProcessingService {
	private final OrderRepository orderRepository;
	private final PlaceOrderRequestMapper placeOrderRequestMapper;

	@Autowired
	public OrderProcessingService(
		OrderRepository orderRepository,
		PlaceOrderRequestMapper placeOrderRequestMapper
	) {
		this.orderRepository = orderRepository;
		this.placeOrderRequestMapper = placeOrderRequestMapper;
	}

	@Transactional
	public Order placeOrder(PlaceOrderRequest placeOrderRequest) throws OrderProcessingException {
		Order order = placeOrderRequestMapper.map(placeOrderRequest);
		order.setOrderState(OrderState.PLACED);
		order = orderRepository.save(order);

		return order;
	}

	public Order verifyOrder(Long orderId) {
		try {
			Order order = orderRepository.findById(orderId).orElseThrow();
			// verify and confirm order
			// TODO: call pickInventoryItem() from the laptop-inventory-service
			order.setOrderState(OrderState.CONFIRMED);
			return orderRepository.save(order);
		} catch (NoSuchElementException ex) {
			throw new OrderProcessingException("Cannot verify the order with id=" + orderId, orderId, ex);
		}
	}

	@Transactional(readOnly = true)
	public Order checkOrderState(Long orderId) {
		try {
			return orderRepository.findById(orderId).orElseThrow();
		} catch (NoSuchElementException ex) {
			throw new OrderProcessingException("Cannot check order state with id=" + orderId, orderId, ex);
		}
	}

	public Order getOrder(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow();
	}
}
