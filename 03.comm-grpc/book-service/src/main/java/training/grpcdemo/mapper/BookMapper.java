package training.grpcdemo.mapper;

import training.grpc.book.BookOuterClass;
import training.grpcdemo.model.Book;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
	collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
	componentModel = "spring"
)
public interface BookMapper {
	BookOuterClass.Book toGrpcBook(Book book);
}
