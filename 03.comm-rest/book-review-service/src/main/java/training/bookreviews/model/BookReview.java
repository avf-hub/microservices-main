package training.bookreviews.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookReview {
	int bookId;
	String bookTitle;
	String bookAuthor;
	Integer bookPageCount;
	String comment;
	String commentAuthorName;
}
