

package store.laptop.catalog.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.model.LaptopRepository;
import store.laptop.catalog.domain.model.StorageType;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "catalog-service", name = "create-initial-data-on-start")
public class InitialDataConfiguration {

	/**
	 * Creates initial data on application start.
	 *
	 * @param repository {@link LaptopRepository}
	 * @return {@link ApplicationRunner}
	 */
	@Bean
	public ApplicationRunner dataInitializer(LaptopRepository repository) {
		return new ApplicationRunner() {
			@Override public void run(ApplicationArguments args) throws Exception {
				List<Laptop> models = List.of(
					Laptop.builder()
						.manufacturer("Apple")
						.model("MacBook Pro")
						.processorLine("M1")
						.ramCapacity(16L)
						.storageType(StorageType.SSD)
						.os("macOS")
						.build(),
					Laptop.builder()
						.manufacturer("HP")
						.model("ProBook")
						.processorLine("Intel Core i5")
						.ramCapacity(8L)
						.storageType(StorageType.SSD)
						.os("Windows 10 Pro")
						.build(),
					Laptop.builder()
						.manufacturer("Dell")
						.model("Latitude")
						.processorLine("Intel Core i3")
						.ramCapacity(8L)
						.storageType(StorageType.SSD)
						.os("Windows 10 Pro")
						.build(),
					Laptop.builder()
						.manufacturer("Lenovo")
						.model("ThinkPad")
						.processorLine("AMD Ryzen 5")
						.ramCapacity(16L)
						.storageType(StorageType.SSD)
						.os("Ubuntu")
						.build()
				);
				repository.saveAll(models);
			}
		};
	}
}
