package training.mousestore.catalog.rest;

import training.mousestore.catalog.dto.MouseDto;
import training.mousestore.catalog.dto.ReviewDto;
import training.mousestore.catalog.mapper.MouseMapper;
import training.mousestore.catalog.model.Mouse;
import training.mousestore.catalog.restclient.ReviewsClient;
import training.mousestore.catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class MouseCatalogController {
	private final CatalogService catalogService;
	private final MouseMapper mapper;
	private final ReviewsClient reviewsClient;

	public MouseCatalogController(
		CatalogService catalogService,
		MouseMapper mapper, ReviewsClient reviewsClient
	) {
		this.catalogService = catalogService;
		this.mapper = mapper;
		this.reviewsClient = reviewsClient;
	}

	@GetMapping
	public ResponseEntity<List<MouseDto>> find() {
		List<MouseDto> list = new ArrayList<>();

		var time1 = System.currentTimeMillis();
		for (Mouse mouse : catalogService.findAll()) {
			MouseDto mouseDto = mapper.toDto(mouse);
			mouseDto.setReviews(getReviewsForMouse(mouse));
			list.add(mouseDto);
		}
		var time2 = System.currentTimeMillis();

		return buildResponseEntity(list, time2 - time1);
	}

	private List<ReviewDto> getReviewsForMouse(Mouse mouse) {
		try {
			List<ReviewDto> reviewsForMouse = reviewsClient.getReviewsForMouse(mouse.getId());
			if (reviewsForMouse != null)
				return reviewsForMouse;

			return Collections.emptyList();
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping("{mouseId}")
	public ResponseEntity<MouseDto> getInfo(@PathVariable Long mouseId) {
		var mouse = catalogService.findById(mouseId);
		MouseDto dto = mapper.toDto(mouse);

		var time1 = System.currentTimeMillis();
		dto.setReviews(getReviewsForMouse(mouse));
		var time2 = System.currentTimeMillis();
		return buildResponseEntity(dto, time2 - time1);
	}

	private ResponseEntity<List<MouseDto>> buildResponseEntity(List<MouseDto> list, long timeToRetieveReviews) {
		boolean reviewsFetchedProperly = list.stream().noneMatch(dto -> dto.getReviews() == null);
		ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
		return annotate(responseBuilder, reviewsFetchedProperly, timeToRetieveReviews).body(list);
	}

	private ResponseEntity<MouseDto> buildResponseEntity(MouseDto mouseDto, long timeToRetieveReviews) {
		boolean reviewsFetchedProperly = mouseDto.getReviews() != null;
		ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
		return annotate(responseBuilder, reviewsFetchedProperly, timeToRetieveReviews).body(mouseDto);
	}

	private ResponseEntity.BodyBuilder annotate(
		ResponseEntity.BodyBuilder responseBuilder,
		boolean reviewsFetchedProperly,
		long timeToRetieveReviews
	) {
		if (reviewsFetchedProperly)
			return responseBuilder;

		return responseBuilder.header(
			"X-MouseStore-Info",
			"Reviews not available"
		).header(
			"X-MouseStore-Reviews-Response-Time",
			timeToRetieveReviews + "ms");
	}
}
