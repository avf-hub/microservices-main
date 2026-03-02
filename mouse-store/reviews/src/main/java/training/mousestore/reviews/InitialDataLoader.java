package training.mousestore.reviews;

import training.mousestore.reviews.model.Review;
import training.mousestore.reviews.repository.ReviewRepository;
import net.datafaker.Faker;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class InitialDataLoader implements ApplicationRunner {
	private final ReviewRepository reviewRepository;
	private final Faker faker;
	private final AppConfig appConfig;
	private final ResourceLoader resourceLoader;

	public InitialDataLoader(
		ReviewRepository reviewRepository,
		Faker faker,
		AppConfig appConfig,
		ResourceLoader resourceLoader
	) {
		this.reviewRepository = reviewRepository;
		this.faker = faker;
		this.appConfig = appConfig;
		this.resourceLoader = resourceLoader;
	}

	@Override public void run(ApplicationArguments args) throws IOException {
		var reviewsPath =
			resourceLoader.getResource("classpath:reviews.txt")
				.getFile().toPath();

		List<String> fakeReviews = Files.readAllLines(reviewsPath);
		Random random = new Random();

		Stream.generate(() ->
				Review.builder()
					.mouseId(random.nextLong(1, appConfig.getMouseCount() + 1))
					.date(faker.date().past(1000, TimeUnit.DAYS).toLocalDateTime())
					.author(faker.app().author())
					.starRating(random.nextInt(1, 6))
					.comment(fakeReviews.get(random.nextInt(fakeReviews.size())))
					.build()
			).limit(appConfig.getMouseCount() * 4)
			.forEach(reviewRepository::save);
	}
}
