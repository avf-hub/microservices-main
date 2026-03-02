package training.mousestore.reviews.mapper;

import training.mousestore.reviews.dto.ReviewDto;
import training.mousestore.reviews.model.Review;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
	@Getter @Setter
	@Autowired private Environment environment;
	private List<String> activeProfiles;

	@PostConstruct
	public void init() {
		this.activeProfiles = Arrays.stream(environment.getActiveProfiles()).toList();
	}

	//@Mapping(target = "reviewId", source = "review.id")
	public abstract ReviewDto toDto(Review review);

	@AfterMapping
	void afterToDto(Review source, @MappingTarget ReviewDto target) {
		if (!activeProfiles.contains("v2"))
			target.setStarRating(null);
	}
}
