package training.grpcdemo.mapper;

import training.grpc.book.BookOuterClass;
import training.grpcdemo.dto.BookDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
	collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
	componentModel = "spring"
)
public interface BookMapper {
	BookDto toDto(BookOuterClass.Book book);
}
