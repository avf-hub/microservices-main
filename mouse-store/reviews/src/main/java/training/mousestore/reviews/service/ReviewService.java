package training.mousestore.reviews.service;

import training.mousestore.reviews.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
	List<Review> findAll();

	Optional<Review> findById(Long id);

	List<Review> findByMouse(Long mouseId);
}
