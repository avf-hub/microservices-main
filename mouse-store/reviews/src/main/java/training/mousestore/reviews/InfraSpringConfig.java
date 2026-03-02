package training.mousestore.reviews;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraSpringConfig {
	@Bean public Faker faker() {
		return new Faker();
	}
}
