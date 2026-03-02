package training.mousestore.reviews.rest;

import training.mousestore.reviews.dto.ReviewDto;
import training.mousestore.reviews.mapper.ReviewMapper;
import training.mousestore.reviews.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2")
public class ReviewsControllerV2 {

	private final ReviewService reviewService;
	private final ReviewMapper mapper;

	public ReviewsControllerV2(ReviewService reviewService, ReviewMapper mapper) {
		this.reviewService = reviewService;
		this.mapper = mapper;
	}

	@GetMapping("mice/{mouseId}")
	public List<ReviewDto> findByMouse(@PathVariable Long mouseId) {
		return
			reviewService.findByMouse(mouseId)
					.stream()
					.map(mapper::toDto)
					// Uncomment to demonstrate dto update in v2
					//.peek(reviewDto -> reviewDto.setNewField("v2"))
					.collect(Collectors.toList());
	}

}
