package training.bookreviews;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class BookReviewsRestApplication {
	@Bean public Faker dataFaker() {
		return new Faker();
	}

	public static void main(String[] args) {
		SpringApplication.run(BookReviewsRestApplication.class, args);
	}
}
