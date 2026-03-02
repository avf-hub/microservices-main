package training.mousestore.reviews.rest;

import training.mousestore.reviews.AppConfig;
import training.mousestore.reviews.dto.ReviewDto;
import training.mousestore.reviews.mapper.ReviewMapper;
import training.mousestore.reviews.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ReviewsController {
	private final ReviewService reviewService;
	private final ReviewMapper mapper;
	private final AppConfig appConfig;

	public ReviewsController(
		ReviewService reviewService,
		ReviewMapper mapper,
		AppConfig appConfig
	) {
		this.reviewService = reviewService;
		this.mapper = mapper;
		this.appConfig = appConfig;
	}

	@GetMapping
	public List<ReviewDto> findAll() {
		appConfig.simulateRandomProblem();
		return
			reviewService.findAll()
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

	@GetMapping("{reviewId}")
	public Optional<ReviewDto> findById(@PathVariable Long reviewId) {
		appConfig.simulateRandomProblem();
		return reviewService.findById(reviewId).map(review -> mapper.toDto(review));
	}

	@GetMapping("byMouse/{mouseId}")
	public List<ReviewDto> findByMouse(@PathVariable Long mouseId) {
		appConfig.simulateRandomProblem();
		return
			reviewService.findByMouse(mouseId)
				.stream()
				.map(mapper::toDto)
				.collect(Collectors.toList());
	}

}
