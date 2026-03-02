package training.bookservice.mapper;

import training.bookservice.dto.BookDto;
import training.bookservice.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
	BookDto toDto(Book book);
}
