

package store.laptop.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import store.laptop.commons.config.LaptopCommonProperties;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(LaptopCommonProperties.class)
public class CatalogServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}
}
