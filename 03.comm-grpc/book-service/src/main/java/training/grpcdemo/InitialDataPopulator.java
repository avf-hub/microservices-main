package training.grpcdemo;

import training.grpcdemo.model.Book;
import training.grpcdemo.repository.BookRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Component
public class InitialDataPopulator implements ApplicationRunner {
	@Autowired private BookRepository bookRepository;

	@Transactional
	@Override public void run(ApplicationArguments args) {
		Faker faker = new Faker();
		Stream.generate(() ->
				Book.builder()
					.author(faker.book().author())
					.title(faker.book().title()).build()
			).limit(1_000)
			.forEach(bookRepository::save);
	}
}
