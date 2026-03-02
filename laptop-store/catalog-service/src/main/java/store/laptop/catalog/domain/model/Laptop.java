

package store.laptop.catalog.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "laptops")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Laptop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String model;
	private String manufacturer;
	private Integer screenSize;
	private String screenResolution;
	private Long ramCapacity;
	private String processorLine;
	private Long processorCoreCount;

	@Enumerated(EnumType.STRING)
	private StorageType storageType;
	private Long driveCapacity;
	private String os;
}
