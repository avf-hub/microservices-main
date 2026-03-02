package store.laptop.ordertracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import store.laptop.ordering.shared.model.web.api.OrderingAPI;

@FeignClient(name = "orders",
	url = "#{serviceCommonConfig['ordering-service'].url}/orders")
public interface OrderingServiceClient extends OrderingAPI {
}
