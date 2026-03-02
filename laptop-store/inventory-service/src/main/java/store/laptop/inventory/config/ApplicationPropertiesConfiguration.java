

package store.laptop.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "inventory-service")
@Data
public class ApplicationPropertiesConfiguration {
	private Boolean createInitialDataOnStart;
}
