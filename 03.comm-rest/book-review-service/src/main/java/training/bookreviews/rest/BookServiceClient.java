package training.bookreviews.rest;

import training.bookservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookServiceClient {
	@GetMapping("/books/{id}")
	BookDto getBookById(@PathVariable("id") long id);

	@GetMapping("/books")
	List<BookDto> getBooks(@RequestParam("limit") Long limit);
}
