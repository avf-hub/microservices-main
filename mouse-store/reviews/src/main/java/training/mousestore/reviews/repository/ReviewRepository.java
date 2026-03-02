package training.mousestore.reviews.repository;

import training.mousestore.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByMouseIdOrderByDateDesc(Long mouseId);
}
