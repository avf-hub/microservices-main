package training.grpcdemo.grpc;

import training.grpc.book.BookOuterClass;
import training.grpc.book.BookServiceGrpc;
import training.grpcdemo.mapper.BookMapper;
import training.grpcdemo.model.Book;
import training.grpcdemo.service.BookService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static training.grpc.book.BookOuterClass.BookResponse;

@GRpcService
public class GrpcBookService extends BookServiceGrpc.BookServiceImplBase {
	private final BookService bookService;
	private final BookMapper mapper;

	@Autowired public GrpcBookService(BookService bookService, BookMapper mapper) {
		this.bookService = bookService;
		this.mapper = mapper;
	}

	@Override public void findById(
		BookOuterClass.BookByIdRequest request,
		StreamObserver<BookResponse> responseObserver) {

		Book book = bookService.findById(request.getId());
		responseObserver.onNext(
			BookResponse.newBuilder()
				.setBook(mapper.toGrpcBook(book))
				.build()
		);
		responseObserver.onCompleted();
	}

	@Override public void getBooks(
		BookOuterClass.BookStreamRequest request,
		StreamObserver<BookResponse> responseObserver) {

		Sort.TypedSort<Book> typedSort = Sort.sort(Book.class);
		Sort sort = switch (request.getSort()) {
			case AUTHOR -> typedSort.by(Book::getAuthor);
			case TITLE, UNRECOGNIZED -> typedSort.by(Book::getTitle);
		};


		bookService.iterateBooks(
			sort, Optional.of(request.getCount()),
			book -> {
				responseObserver.onNext(
					BookResponse.newBuilder()
						.setBook(mapper.toGrpcBook(book))
						.build());

				sleepABit();
			}
		);

		responseObserver.onCompleted();
	}

	private static void sleepABit() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createBook(BookOuterClass.CreateBookRequest request, StreamObserver<BookOuterClass.CreateBookResponse> responseObserver) {
		Book savedBook = bookService.createBook(request.getTitle(), request.getAuthor());
		// Send the response with the ID of the newly created book
		BookOuterClass.CreateBookResponse response = BookOuterClass.CreateBookResponse.newBuilder()
				.setId(savedBook.getId())
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
