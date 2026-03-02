package training.grpcdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReviewDto {
	private final BookDto book;
	private final String reviewAuthor;
	private final String reviewComment;
}
