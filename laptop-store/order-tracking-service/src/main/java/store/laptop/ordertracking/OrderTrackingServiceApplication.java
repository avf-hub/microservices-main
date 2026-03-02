

package store.laptop.ordertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderTrackingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderTrackingServiceApplication.class, args);
	}
}
