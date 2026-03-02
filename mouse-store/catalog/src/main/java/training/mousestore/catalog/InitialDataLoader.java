package training.mousestore.catalog;

import training.mousestore.catalog.dto.MouseDto;
import training.mousestore.catalog.mapper.MouseMapper;
import training.mousestore.catalog.repository.MouseRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationRunner {
	private final MouseMapper mapper;
	private final MouseRepository mouseRepository;

	public InitialDataLoader(MouseMapper mapper,
	                         MouseRepository mouseRepository) {
		this.mapper = mapper;
		this.mouseRepository = mouseRepository;
	}

	@Override public void run(ApplicationArguments args) throws Exception {
		try (Reader reader =
			     new InputStreamReader(
				     ResourceUtils
					     .getURL("classpath:mice.txt")
					     .openStream())
		) {
			List<MouseDto> mice =
				new CsvToBeanBuilder(reader)
					.withType(MouseDto.class)
					.build()
					.parse();

			mice.stream()
				.map(mapper::fromDto)
				.forEach(mouseRepository::save);
		}
	}
}
