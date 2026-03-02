

package store.laptop.catalog.domain.specification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import store.laptop.catalog.config.InitialDataConfiguration;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.model.LaptopRepository;
import store.laptop.catalog.domain.model.StorageType;
import store.laptop.commons.config.LaptopCommonAutoConfiguration;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.jpa.domain.Specification.where;
import static store.laptop.catalog.domain.model.LaptopSpecifications.*;

@DataJpaTest
@Import({InitialDataConfiguration.class, LaptopCommonAutoConfiguration.class})
class LaptopSpecificationTests {
	@Autowired private LaptopRepository laptopRepository;

	private final Laptop laptop =
		Laptop.builder()
			.manufacturer("Apple")
			.model("MacBook Pro")
			.os("macOS")
			.processorLine("M1")
			.storageType(StorageType.SSD)
			.build();

	@Test
	void testSpecification() {
		List<Laptop> results = laptopRepository.findAll(
			where(madeBy("Apple")
				.and(modelIs("MacBook Pro"))
				.and(hasStorageType(StorageType.SSD))
				.and(hasProcessorLine("Intel Core i5").or(hasProcessorLine("M1")))
			)
		);

		assertNotNull(results);
		assertEquals(1, results.size());

		Laptop resultLaptop = results.get(0);
		assertEquals(laptop.getManufacturer(), resultLaptop.getManufacturer());
		assertEquals(laptop.getModel(), resultLaptop.getModel());
		assertEquals(laptop.getOs(), resultLaptop.getOs());
		assertEquals(laptop.getProcessorLine(), resultLaptop.getProcessorLine());
		assertEquals(laptop.getStorageType(), resultLaptop.getStorageType());
	}

	@ParameterizedTest
	@MethodSource("provideVariants")
	void testManufacturerAndProcessorLine(String manufacturer, String processorLine) {
		List<Laptop> results = laptopRepository.findAll(
			where(madeBy(manufacturer)
				.and(hasProcessorLine(processorLine))
			)
		);

		assertNotNull(results);
		assertFalse(results.isEmpty());
		assertThat(results).allMatch(model -> manufacturer.equals(model.getManufacturer())
			&& processorLine.equals(model.getProcessorLine()));
	}

	private static Stream<Arguments> provideVariants() {
		return Stream.of(
			Arguments.of("Apple", "M1"),
			Arguments.of("HP", "Intel Core i5"),
			Arguments.of("Dell", "Intel Core i3"),
			Arguments.of("Lenovo", "AMD Ryzen 5")
		);
	}
}
