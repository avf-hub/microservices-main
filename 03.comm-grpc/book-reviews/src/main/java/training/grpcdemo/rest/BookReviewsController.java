package training.grpcdemo.rest;

import training.grpc.book.BookOuterClass;
import training.grpc.book.BookServiceGrpc;
import training.grpcdemo.AppConfig;
import training.grpcdemo.dto.BookReviewDto;
import training.grpcdemo.mapper.BookMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class BookReviewsController {
	private final AppConfig appConfig;
	private final BookMapper bookMapper;
	private final Faker faker;
	private ManagedChannel channel;
	private BookServiceGrpc.BookServiceBlockingStub client;

	public BookReviewsController(
		AppConfig appConfig,
		BookMapper bookMapper,
		Faker faker
	) {
		this.appConfig = appConfig;
		this.bookMapper = bookMapper;
		this.faker = faker;
	}

	@PostConstruct
	public void init() {
		this.channel =
			ManagedChannelBuilder
				.forAddress(
					appConfig.getBookServiceAddress().host(),
					appConfig.getBookServiceAddress().port()
				)
				.usePlaintext()
				.build();

		this.client = BookServiceGrpc.newBlockingStub(channel);
	}

	@PreDestroy
	public void onDestroy() {
		this.channel.shutdown();
	}

	@GetMapping("byBook/{bookId}")
	public List<BookReviewDto> findReviews(@PathVariable Long bookId) {
		var request =
			BookOuterClass.BookByIdRequest
				.newBuilder()
				.setId(bookId)
				.build();

		BookOuterClass.BookResponse response = client.findById(request);
		BookOuterClass.Book book = response.getBook();

		var reviews = new LinkedList<BookReviewDto>();
		reviews.add(
			BookReviewDto.builder()
				.book(bookMapper.toDto(book))
				.reviewAuthor(faker.app().author())
				.reviewComment("This is an amazing book!")
				.build()
		);

		reviews.add(
			BookReviewDto.builder()
				.book(bookMapper.toDto(book))
				.reviewAuthor(faker.app().author())
				.reviewComment("I did not like that book...")
				.build()
		);

		return reviews;
	}
}
