package training.mousestore.catalog.service.impl;

import training.mousestore.catalog.model.Mouse;
import training.mousestore.catalog.repository.MouseRepository;
import training.mousestore.catalog.service.CatalogService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CatalogServiceImpl implements CatalogService {
	private final MouseRepository mouseRepository;

	public CatalogServiceImpl(MouseRepository mouseRepository) {
		this.mouseRepository = mouseRepository;
	}

	@Transactional(readOnly = true)
	@Override public Mouse findById(Long id) {
		return mouseRepository.findById(id).orElseThrow();
	}

	@Transactional(readOnly = true)
	@Override public List<Mouse> findAll() {
		return mouseRepository.findAll(Sort.sort(Mouse.class).by(Mouse::getName));
	}
}
