

package store.laptop.ordering.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.laptop.ordering.client.InventoryServiceClient;
import store.laptop.ordering.controller.dto.mapping.OrderDataMapper;
import store.laptop.ordering.controller.dto.mapping.PlaceOrderRequestMapper;
import store.laptop.ordering.domain.exception.OrderProcessingException;
import store.laptop.ordering.domain.model.*;
import store.laptop.ordering.shared.model.web.dto.ItemReservationRequest;
import store.laptop.ordering.shared.model.web.dto.PlaceOrderRequest;
import store.laptop.ordering.shared.model.web.dto.ReservationRecordInfo;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class OrderProcessingService {
    private final OrderRepository orderRepository;
    private final PlaceOrderRequestMapper placeOrderRequestMapper;
    private final InventoryServiceClient inventoryClient;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final OutboxMessageRepository outboxMessageRepository;
    private final OrderDataMapper orderDataMapper;

    @Autowired
    public OrderProcessingService(
            OrderRepository orderRepository,
            PlaceOrderRequestMapper placeOrderRequestMapper,
            InventoryServiceClient inventoryClient,
            OutboxMessageRepository outboxMessageRepository,
            OrderDataMapper orderDataMapper
    ) {
        this.orderRepository = orderRepository;
        this.placeOrderRequestMapper = placeOrderRequestMapper;
        this.inventoryClient = inventoryClient;
        this.outboxMessageRepository = outboxMessageRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public Order placeOrder(PlaceOrderRequest placeOrderRequest) throws OrderProcessingException {
        Order order = placeOrderRequestMapper.map(placeOrderRequest);
        order.setOrderState(OrderState.PLACED);
        order = orderRepository.save(order);

        try {
            OutboxMessage outboxMessage = new OutboxMessage();
            String orderDataJson = jsonMapper.writeValueAsString(
                    orderDataMapper.map(order));
            outboxMessage.setMessage(orderDataJson);
            outboxMessageRepository.save(outboxMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public Order verifyOrder(Long orderId) {
        AtomicReference<OrderState> state = new AtomicReference<>(OrderState.CONFIRMED);
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.getItems().forEach(item -> {
                // verify and confirm order
                ReservationRecordInfo reservationInfo = inventoryClient.pickInventoryItem(ItemReservationRequest.builder()
                        .inventoryItemId(item.getProductId())
                        .requiredQty(item.getQty())
                        .build());
                if (Objects.isNull(reservationInfo)) {
                    state.set(OrderState.CANCELED);
                }
            });
            order.setOrderState(state.get());
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
