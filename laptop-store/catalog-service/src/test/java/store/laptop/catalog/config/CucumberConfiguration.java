

package store.laptop.catalog.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Allows using Spring Context in Cucumber tests.
 */
@CucumberContextConfiguration
@SpringBootTest
public class CucumberConfiguration {
}
