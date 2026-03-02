

package store.laptop.catalog;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.model.StorageType;
import store.laptop.catalog.domain.service.LaptopSearchingService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static store.laptop.catalog.domain.model.LaptopSpecifications.*;

@Configurable
public class FindLaptopsByCriteriaSteps {
	@Autowired LaptopSearchingService searchingService;

	private List<Laptop> suitableLaptops = new ArrayList<>();
	private Specification<Laptop> customerCriteria;
	private Laptop desiredLaptop;

	@Given("Customer wants to find {string} {string} with {string} processor and {string} drive")
	public void defineSpecificCriteria(String manufacturer, String model, String processor, String storageType) {
		desiredLaptop = Laptop.builder()
			.manufacturer(manufacturer)
			.model(model)
			.processorLine(processor)
			.storageType(StorageType.valueOf(storageType))
			.build();

		customerCriteria = Specification
			.where(madeBy(desiredLaptop.getManufacturer())
				.and(modelIs(desiredLaptop.getModel()))
				.and(hasProcessorLine(desiredLaptop.getProcessorLine()))
				.and(hasStorageType(desiredLaptop.getStorageType()))
			);
	}

	@When("Customer tries to find models by defined criteria")
	public void findLaptopsByCriteria() {
		suitableLaptops = searchingService.findBySpecification(customerCriteria);
	}

	@Then("Customer should get suitable models")
	public void verifyResult() {
		assertThat(suitableLaptops, notNullValue());
		assertThat(suitableLaptops.isEmpty(), is(false));

		for (Laptop laptop : suitableLaptops) {

			// verify manufacturer
			assertThat(laptop.getManufacturer(),
				equalTo(desiredLaptop.getManufacturer()));

			// verify model name
			assertThat(laptop.getModel(),
				equalTo(desiredLaptop.getModel()));

			// verify processor
			assertThat(laptop.getProcessorLine(),
				equalTo(desiredLaptop.getProcessorLine()));

			// verify storage type
			assertThat(laptop.getStorageType(),
				equalTo(desiredLaptop.getStorageType()));
		}
	}
}
