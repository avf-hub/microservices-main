package store.laptop.ordering.domain.service;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import store.laptop.ordering.AppConfig;
import store.laptop.ordering.shared.model.web.dto.OrderData;

@Service
public class OrderMessagingPublisher {
    private final StreamBridge streamBridge;
    private final AppConfig config;

    public OrderMessagingPublisher(StreamBridge streamBridge, AppConfig config) {
        this.streamBridge = streamBridge;
        this.config = config;
    }

    public void publish(OrderData orderData) {
        if (config.isDisableMessagePublishing())
            return;

        streamBridge.send("orders", orderData);
    }
}
