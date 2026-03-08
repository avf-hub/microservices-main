package training.grpcdemo.service.impl;

import training.grpcdemo.model.Book;
import training.grpcdemo.repository.BookRepository;
import training.grpcdemo.service.BookService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Getter @Setter
@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional(readOnly = true)
	@Override public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow();
	}

	@Transactional(readOnly = true)
	@Override public void iterateBooks(Sort sort, Optional<Long> limit, Consumer<Book> consumer) {
		Stream<Book> stream = bookRepository.findAllBy(sort);
		if (limit.isPresent())
			stream = stream.limit(limit.get());

		stream.forEach(consumer);
	}

	@Override
	public Book createBook(String title, String author) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		return bookRepository.save(book);
	}
}
