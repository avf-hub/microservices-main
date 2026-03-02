package training.grpcdemo.repository;

import training.grpcdemo.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {
	Stream<Book> findAllBy(Sort sort);
}
