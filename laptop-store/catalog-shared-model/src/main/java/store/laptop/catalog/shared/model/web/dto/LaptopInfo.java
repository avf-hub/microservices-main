

package store.laptop.catalog.shared.model.web.dto;

import lombok.Data;

@Data
public class LaptopInfo {

	private String model;
	private String manufacturer;
	private Integer screenSize;
	private String screenResolution;
	private Long ramCapacity;
	private String processorLine;
	private Long processorCoreCount;
	private String storageType;
	private Long driveCapacity;
	private String os;
}
