

package store.laptop.inventory.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.laptop.inventory.domain.model.InventoryItem;
import store.laptop.inventory.domain.model.InventoryItemRepository;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "inventory-service", name = "create-initial-data-on-start")
public class InitialDataConfiguration {

	/**
	 * Creates initial data on application start.
	 *
	 * @param repository {@link InventoryItemRepository}
	 * @return {@link ApplicationRunner}
	 */
	@Bean
	public ApplicationRunner dataInitializer(InventoryItemRepository repository) {
		return args -> {
			List<InventoryItem> inventoryItems = List.of(
				InventoryItem.builder()
					.productId(1L)
					.unitCost(new BigDecimal(3500))
					.availableQty(10L)
					.build(),
				InventoryItem.builder()
					.productId(2L)
					.unitCost(new BigDecimal(2700))
					.availableQty(10L)
					.build()
			);
			repository.saveAll(inventoryItems);
		};
	}
}
