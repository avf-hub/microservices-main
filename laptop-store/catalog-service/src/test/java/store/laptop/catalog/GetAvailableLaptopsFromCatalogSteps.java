

package store.laptop.catalog;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.service.LaptopSearchingService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Cucumber steps definition.
 * <p>
 * See 'test/resources/cucumber' directory and '*.feature' files.
 */
@Configurable
public class GetAvailableLaptopsFromCatalogSteps {

	@Autowired
	LaptopSearchingService searchingService;

	private List<Laptop> availableLaptops = new ArrayList<>();

	@Given("Catalog contains a few laptop models")
	public void initializeProductCatalog() {
		// The catalog should be already initialized by
		// Spring Boot ApplicationRunner inside the main class.
	}

	@When("Customer tries to get available models")
	public void findAllLaptops() {
		Specification<Laptop> spec = Specification.where(null);
		availableLaptops = searchingService.findBySpecification(spec);
	}

	@Then("Customer should get all models")
	public void checkResult() {
		assertThat("The list of models should not be empty.",
			availableLaptops.isEmpty(), is(false));
	}
}
