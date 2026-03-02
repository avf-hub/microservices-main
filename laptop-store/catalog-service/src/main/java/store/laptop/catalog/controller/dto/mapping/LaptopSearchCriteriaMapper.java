

package store.laptop.catalog.controller.dto.mapping;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.jpa.domain.Specification;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.model.StorageType;
import store.laptop.catalog.shared.model.web.dto.LaptopSearchCriteria;

import static store.laptop.catalog.domain.model.LaptopSpecifications.*;

public class LaptopSearchCriteriaMapper {

	private LaptopSearchCriteriaMapper() {
		// do nothing
	}

	public Specification<Laptop> map(LaptopSearchCriteria criteria) {
		Specification<Laptop> spec = Specification.where(null);

		if (criteria == null) {
			// Return empty specification.
			return spec;
		}

		// Mapping for the manufacturers field.
		if (!criteria.getManufacturer().isEmpty()) {
			Specification<Laptop> madeBySpec = Specification.where(null);
			for (String manufacturer : criteria.getManufacturer()) {
				madeBySpec = madeBySpec.or(madeBy(manufacturer));
			}
			spec = spec.and(madeBySpec);
		}

		// Mapping for the models field.
		if (!criteria.getModel().isEmpty()) {
			Specification<Laptop> modelSpec = Specification.where(null);
			for (String model : criteria.getModel()) {
				modelSpec = modelSpec.or(modelIs(model));
			}
			spec = spec.and(modelSpec);
		}

		// Mapping for the storageTypes field.
		if (!criteria.getStorageType().isEmpty()) {
			Specification<Laptop> storageSpec = Specification.where(null);
			for (String storageType : criteria.getStorageType()) {
				storageSpec = storageSpec.or(hasStorageType(
					EnumUtils.getEnumIgnoreCase(StorageType.class, storageType))
				);
			}
			spec = spec.and(storageSpec);
		}

		// Mapping for the ramCapacity field.
		if (!criteria.getRamCapacity().isEmpty()) {
			Specification<Laptop> ramCapacitySpec = Specification.where(null);
			for (Long ramCapacity : criteria.getRamCapacity()) {
				ramCapacitySpec = ramCapacitySpec.or(hasRamCapacity(ramCapacity));
			}
			spec = spec.and(ramCapacitySpec);
		}
		return spec;
	}

	/**
	 * This factory method creates new instance of mapper.
	 *
	 * @return {@link LaptopSearchCriteriaMapper}
	 */
	public static LaptopSearchCriteriaMapper newMapper() {
		return new LaptopSearchCriteriaMapper();
	}
}
