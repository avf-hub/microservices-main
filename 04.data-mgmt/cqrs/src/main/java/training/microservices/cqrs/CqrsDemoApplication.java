package training.microservices.cqrs;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CqrsDemoApplication {
	@Bean public Faker faker() {
		return new Faker();
	}

	public static void main(String[] args) {
		SpringApplication.run(CqrsDemoApplication.class, args);
	}
}
