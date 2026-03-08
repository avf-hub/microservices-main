package store.laptop.ordering.client;

import org.springframework.cloud.openfeign.FeignClient;
import store.laptop.ordering.shared.model.web.api.InventoryAPI;

@FeignClient(name = "inventory",
        url = "#{serviceCommonConfig['inventory-service'].url}/inventory")
public interface InventoryServiceClient extends InventoryAPI {
}
