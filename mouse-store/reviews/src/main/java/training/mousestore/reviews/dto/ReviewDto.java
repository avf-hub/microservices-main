package training.mousestore.reviews.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {
	private Long mouseId;
	private LocalDateTime date;
	private String author;
	private String comment;
	private Integer starRating;
	// uncomment to show API versioning:
	// now we want to additionally pass review id
	// also, uncomment in ReviewMapper
	//private String reviewId;
	//private String newField;
}
