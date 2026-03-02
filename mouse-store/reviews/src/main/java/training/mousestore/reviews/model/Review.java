package training.mousestore.reviews.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	@Id @GeneratedValue
	private Long id;
	// this is not a foreign key in database sense, just a reference
	@Column(nullable = false) private Long mouseId;
	@Column(nullable = false) private LocalDateTime date;
	@Column(nullable = false) private String author;
	@Column(nullable = false) private String comment;
	@Column(nullable = false) private Integer starRating;
}
