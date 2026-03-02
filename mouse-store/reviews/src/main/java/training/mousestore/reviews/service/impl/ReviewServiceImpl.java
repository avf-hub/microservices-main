package training.mousestore.reviews.service.impl;

import training.mousestore.reviews.model.Review;
import training.mousestore.reviews.repository.ReviewRepository;
import training.mousestore.reviews.service.ReviewService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	@Transactional(readOnly = true)
	@Override public Optional<Review> findById(Long id) {
		return reviewRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override public List<Review> findAll() {
		return reviewRepository
			.findAll(
				Sort.sort(Review.class)
					.by(Review::getDate)
					.descending()
			);
	}

	@Transactional(readOnly = true)
	@Override public List<Review> findByMouse(Long mouseId) {
		return reviewRepository.findByMouseIdOrderByDateDesc(mouseId);
	}
}
