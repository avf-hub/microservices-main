package training.grpcdemo.rest;

import training.grpc.book.BookOuterClass;
import training.grpc.book.BookServiceGrpc;
import training.grpcdemo.AppConfig;
import training.grpcdemo.dto.BookDto;
import training.grpcdemo.dto.BookReviewDto;
import training.grpcdemo.mapper.BookMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class BookReviewsAsyncController {
	private final AppConfig appConfig;
	private final Faker faker;
	private final BookMapper bookMapper;

	private ManagedChannel channel;
	private BookServiceGrpc.BookServiceStub client;

	public BookReviewsAsyncController(AppConfig appConfig, Faker faker, BookMapper bookMapper) {
		this.appConfig = appConfig;
		this.faker = faker;
		this.bookMapper = bookMapper;
	}

	@PostConstruct
	public void init() {
		this.channel =
			ManagedChannelBuilder.forAddress(
					appConfig.getBookServiceAddress().host(),
					appConfig.getBookServiceAddress().port()
				)
				.usePlaintext()
				.build();

		this.client = BookServiceGrpc.newStub(channel);
	}

	@PreDestroy
	public void onDestroy() {
		this.channel.shutdown();
	}

	@Getter
	private class BooksResponseObserver implements StreamObserver<BookOuterClass.BookResponse> {
		private final List<BookDto> books = new LinkedList<>();
		CompletableFuture<List<BookDto>> responseFuture =
			new CompletableFuture<>();

		@Override public void onNext(BookOuterClass.BookResponse bookResponse) {
			books.add(BookReviewsAsyncController.this.bookMapper.toDto(bookResponse.getBook()));
		}

		@Override public void onError(Throwable throwable) {
			responseFuture.completeExceptionally(throwable);
		}

		@Override public void onCompleted() {
			responseFuture.complete(books);
		}
	}


	@GetMapping("/reviews")
	public CompletableFuture<List<BookReviewDto>> listReviews() {
		// let's get 10 first book, for each book let's produce 2 fake reviews
		var request = BookOuterClass.BookStreamRequest.newBuilder()
			.setCount(5)
			.setSort(BookOuterClass.BookSort.TITLE)
			.build();

		var responseObserver = new BooksResponseObserver();
		client.getBooks(request, responseObserver);

		return responseObserver.getResponseFuture()
			.thenApply(bookDtos -> {
				List<BookReviewDto> reviews = new LinkedList<>();

				bookDtos.forEach(bookDto -> {
					reviews.add(
						BookReviewDto.builder()
							.book(bookDto)
							.reviewAuthor(faker.app().author())
							.reviewComment("This is an amazing book!")
							.build()
					);

					reviews.add(
						BookReviewDto.builder()
							.book(bookDto)
							.reviewAuthor(faker.app().author())
							.reviewComment("I did not like that book...")
							.build()
					);
				});

				return reviews;
			});
	}
}
