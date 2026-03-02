package training.bookservice.rest;

import training.bookservice.dto.BookDto;
import training.bookservice.mapper.BookMapper;
import training.bookservice.model.Book;
import training.bookservice.service.BookService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
	private final BookService bookService;
	private final BookMapper mapper;

	public BookController(BookService bookService,
	                      BookMapper mapper) {
		this.bookService = bookService;
		this.mapper = mapper;
	}

	@GetMapping("/books/{id}")
	public BookDto getBookById(@PathVariable long id) {
		Book book = bookService.findById(id);
		return mapper.toDto(book);
	}

	@GetMapping("/books")
	public List<BookDto> getBooks(
		@RequestParam(value = "limit", required = false) Long limit
	) {
		List<BookDto> result = new LinkedList<>();
		bookService.iterateBooks(
			Sort.sort(BookDto.class).by(BookDto::getTitle),
			Optional.ofNullable(limit),
			book -> result.add(mapper.toDto(book))
		);
		return result;
	}
}
