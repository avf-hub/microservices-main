package training.mousestore.catalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class ReviewDto {
	private LocalDateTime date;
	private String author;
	private String comment;
	private Integer starRating;
}
