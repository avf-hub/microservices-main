

package store.laptop.template.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StubData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
