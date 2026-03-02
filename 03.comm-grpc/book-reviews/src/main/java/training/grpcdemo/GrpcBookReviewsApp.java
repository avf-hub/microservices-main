package training.grpcdemo;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GrpcBookReviewsApp {
	@Bean public Faker faker() {
		return new Faker();
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcBookReviewsApp.class, args);
	}
}
