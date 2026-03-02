

package store.laptop.catalog.domain.model;

import org.springframework.data.jpa.domain.Specification;

/**
 * Helper class with specification set for the flexible searching of {@link Laptop}.
 * <p>
 * ATTENTION!
 * Some string arguments, such as {@literal processorLine} or {@literal manufacturer} are case-sensitive.
 * For details, see the concrete comment above each method.
 */
public class LaptopSpecifications {

	private LaptopSpecifications() {
		// do nothing
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param modelName can be {@literal null}. This value is case-sensitive.
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> modelIs(String modelName) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.MODEL),
			modelName);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param manufacturer can be {@literal null}. This value is case-sensitive.
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> madeBy(String manufacturer) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.manufacturer),
			manufacturer);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param screenSize can be {@literal null}
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasScreenSize(Integer screenSize) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.screenSize),
			screenSize);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param screenSize can be {@literal null}
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasScreenSizeGreaterThanOrEqualTo(Integer screenSize) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Laptop_.screenSize),
			screenSize);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param screenSize can be {@literal null}
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasScreenSizeLessThanOrEqualTo(Integer screenSize) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Laptop_.screenSize),
			screenSize);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param modelName can be {@literal null}. This value is NOT case-sensitive.
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> modelLike(String modelName) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Laptop_.MODEL),
			modelName);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param storageType can be {@literal null}
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasStorageType(StorageType storageType) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.storageType),
			storageType);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param processorLine can be {@literal null}. This value is case-sensitive.
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasProcessorLine(String processorLine) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.processorLine),
			processorLine);
	}

	/**
	 * The given {@link LaptopSpecifications} to the current one.
	 *
	 * @param ramCapacity can be {@literal null}
	 * @return The conjunction of the specifications
	 */
	public static Specification<Laptop> hasRamCapacity(Long ramCapacity) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Laptop_.ramCapacity),
			ramCapacity);
	}
}
