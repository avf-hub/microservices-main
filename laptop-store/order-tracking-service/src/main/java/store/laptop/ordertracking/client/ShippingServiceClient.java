package store.laptop.ordertracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import store.laptop.shipping.shared.model.web.api.ShippingAPI;

@FeignClient(name = "shipping",
        url = "#{serviceCommonConfig['shipping-service'].url}/shipping")
public interface ShippingServiceClient extends ShippingAPI {
}
