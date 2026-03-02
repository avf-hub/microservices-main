

package store.laptop.catalog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.domain.model.LaptopRepository;

import java.util.List;

@Service
public class LaptopSearchingService {
	private final LaptopRepository laptopRepository;

	@Autowired
	public LaptopSearchingService(LaptopRepository laptopRepository) {
		this.laptopRepository = laptopRepository;
	}

	public Laptop findById(Long productId) {
		return laptopRepository.findById(productId)
			.orElse(null);
	}

	public List<Laptop> findBySpecification(Specification<Laptop> spec) {
		return laptopRepository.findAll(spec);
	}
}
