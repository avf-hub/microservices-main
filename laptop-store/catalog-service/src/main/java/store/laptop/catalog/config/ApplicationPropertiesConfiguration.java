

package store.laptop.catalog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "catalog-service")
@Data
public class ApplicationPropertiesConfiguration {

	/**
	 * Creates initial data on application start.
	 */
	private Boolean createInitialDataOnStart;
}
