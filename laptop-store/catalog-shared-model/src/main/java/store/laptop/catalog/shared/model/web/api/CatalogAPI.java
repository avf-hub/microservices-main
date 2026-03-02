package store.laptop.catalog.shared.model.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import store.laptop.catalog.shared.model.web.dto.LaptopInfo;
import store.laptop.catalog.shared.model.web.dto.LaptopSearchCriteria;

import java.util.List;

public interface CatalogAPI {
	@GetMapping("{laptopId}")
	LaptopInfo getLaptopInfoById(@PathVariable Long laptopId);

	@GetMapping
	List<LaptopInfo> findByCriteria(LaptopSearchCriteria criteria);
}
