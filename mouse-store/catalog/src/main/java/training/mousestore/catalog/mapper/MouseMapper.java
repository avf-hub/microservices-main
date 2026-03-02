package training.mousestore.catalog.mapper;

import training.mousestore.catalog.dto.MouseDto;
import training.mousestore.catalog.model.Mouse;
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
public abstract class MouseMapper {
	@Getter @Setter
	@Autowired private Environment environment;
	private List<String> activeProfiles;

	@PostConstruct
	public void init() {
		this.activeProfiles = Arrays.asList(environment.getActiveProfiles());
	}

	public abstract MouseDto toDto(Mouse mouse);

	public abstract Mouse fromDto(MouseDto dto);

	@AfterMapping
	void afterToDto(Mouse source, @MappingTarget MouseDto target) {
		if (!activeProfiles.contains("v2"))
			target.setDpi(null);
	}
}
