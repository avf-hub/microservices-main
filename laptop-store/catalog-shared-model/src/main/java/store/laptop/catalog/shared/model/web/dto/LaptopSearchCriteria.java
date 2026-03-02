

package store.laptop.catalog.shared.model.web.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LaptopSearchCriteria {

	private Set<String> manufacturer = new HashSet<>();
	private Set<String> model = new HashSet<>();
	private Set<String> storageType = new HashSet<>();
	private Set<Long> ramCapacity = new HashSet<>();
}
