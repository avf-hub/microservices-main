package training.mousestore.catalog.service;

import training.mousestore.catalog.model.Mouse;

import java.util.List;

public interface CatalogService {
	Mouse findById(Long id);
	List<Mouse> findAll();
}
