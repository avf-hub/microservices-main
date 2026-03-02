package training.bookservice.service;

import training.bookservice.model.Book;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;

public interface BookService {
	Book findById(Long id);

	void iterateBooks(Sort sort, Optional<Long> limit, Consumer<Book> consumer);
}
