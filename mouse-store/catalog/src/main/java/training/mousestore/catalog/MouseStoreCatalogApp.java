package training.mousestore.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MouseStoreCatalogApp {
	public static void main(String[] args) {
		SpringApplication.run(MouseStoreCatalogApp.class, args);
	}
}
