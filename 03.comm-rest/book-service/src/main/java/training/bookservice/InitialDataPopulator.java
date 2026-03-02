package training.bookservice;

import training.bookservice.model.Book;
import training.bookservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Stream;

@Component
public class InitialDataPopulator implements ApplicationRunner {
	@Autowired private BookRepository bookRepository;

	@Transactional
	@Override public void run(ApplicationArguments args) {
		// generate fake, but good quality test data
		Faker faker = new Faker();
		Random random = new Random();
		Stream.generate(() ->
			Book.builder()
				.author(faker.book().author())
				.title(faker.book().title())
				.pageCount(random.nextInt(100, 500))
				.build()
		).limit(1_000)
			.forEach(bookRepository::save);
	}
}
