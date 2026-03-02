

package store.laptop.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.catalog.controller.dto.mapping.LaptopInfoMapper;
import store.laptop.catalog.controller.dto.mapping.LaptopSearchCriteriaMapper;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.service.LaptopSearchingService;
import store.laptop.catalog.shared.model.web.api.CatalogAPI;
import store.laptop.catalog.shared.model.web.dto.LaptopInfo;
import store.laptop.catalog.shared.model.web.dto.LaptopSearchCriteria;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
public class LaptopController implements CatalogAPI {
	private final LaptopSearchingService searchingService;
	private final LaptopInfoMapper laptopInfoMapper;

	@Autowired
	public LaptopController(LaptopSearchingService searchingService,
	                        LaptopInfoMapper laptopInfoMapper) {
		this.searchingService = searchingService;
		this.laptopInfoMapper = laptopInfoMapper;
	}

	@Override
	public LaptopInfo getLaptopInfoById(Long laptopId) {
		Laptop laptop = searchingService.findById(laptopId);
		return laptopInfoMapper.map(laptop);
	}

	@Override
	public List<LaptopInfo> findByCriteria(LaptopSearchCriteria criteria) {
		Specification<Laptop> spec = LaptopSearchCriteriaMapper.newMapper().map(criteria);
		return searchingService.findBySpecification(spec).stream()
			.map(laptopInfoMapper::map)
			.collect(Collectors.toList());
	}
}
