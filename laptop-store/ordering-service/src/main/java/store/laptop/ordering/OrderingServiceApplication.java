

package store.laptop.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class OrderingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderingServiceApplication.class, args);
	}
}
