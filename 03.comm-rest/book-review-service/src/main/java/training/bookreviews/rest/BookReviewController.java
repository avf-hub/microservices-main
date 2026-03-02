package training.bookreviews.rest;

import training.bookreviews.model.BookReview;
import training.bookservice.dto.BookDto;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class BookReviewController {
	private final BookServiceClient bookServiceClient;
	private final Faker faker;

	public BookReviewController(
		BookServiceClient bookServiceClient,
		Faker faker
	) {
		this.bookServiceClient = bookServiceClient;
		this.faker = faker;
	}

	@GetMapping("/reviews/{id}")
	public BookReview getReviewById(@PathVariable Long id) {
		BookDto book = bookServiceClient.getBookById(id);
		return buildFakeReview(book);
	}

	@GetMapping("/reviews")
	public List<BookReview> getReviews(@RequestParam("bookId") List<Long> bookIds) {
		List<BookReview> reviews = new LinkedList<>();
		bookIds.forEach(
			bookId -> {
				BookDto book = bookServiceClient.getBookById(bookId);
				BookReview review = buildFakeReview(book);
				reviews.add(review);
			}
		);
		return reviews;
	}

	private BookReview buildFakeReview(BookDto bookDto) {
		return new BookReview(
			bookDto.getId(),
			bookDto.getTitle(),
			bookDto.getAuthor(),
			bookDto.getPageCount(),
			"Splendid book!",
			faker.funnyName().name()
		);
	}
}
