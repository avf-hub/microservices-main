package training.mousestore.catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Mouse {
	@Id @GeneratedValue
	private Long id;
	private String ean;
	private String name;
	private String vendor;
	private MouseConnectionType connectionType;
	private String dpi;
}
