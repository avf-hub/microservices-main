package training.mousestore.catalog.restclient;

import training.mousestore.catalog.dto.ReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("reviews")
public interface ReviewsClient {
	@GetMapping("byMouse/{mouseId}")
	List<ReviewDto> getReviewsForMouse(@PathVariable("mouseId") Long mouseId);
}
