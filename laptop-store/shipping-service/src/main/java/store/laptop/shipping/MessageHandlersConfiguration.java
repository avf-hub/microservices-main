package store.laptop.shipping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.laptop.shipping.service.ShippingService;

import java.util.function.Consumer;

@Configuration
public class MessageHandlersConfiguration {
    private final ShippingService shippingService;

    public MessageHandlersConfiguration(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Bean
    public Consumer<Long> shippingAfterPayment() {
        return shippingService::startShipping;
    }
}
