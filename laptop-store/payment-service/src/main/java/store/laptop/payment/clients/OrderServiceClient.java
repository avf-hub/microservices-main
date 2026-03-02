package store.laptop.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import store.laptop.ordering.shared.model.web.api.OrderingAPI;

@FeignClient(name = "ordering",
        url = "#{serviceCommonConfig['ordering-service'].url}/orders")
public interface OrderServiceClient extends OrderingAPI {
}
