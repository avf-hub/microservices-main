package training.bookservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Book {
	@Id @GeneratedValue
	private Long id;
	private String title;
	private int pageCount;
	private String author;
}
