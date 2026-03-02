package training.mousestore.catalog.dto;


import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class MouseDto {
	@CsvBindByName
	private String name;
	@CsvBindByName
	private String vendor;
	@CsvBindByName
	private String ean;
	@CsvBindByName
	private String connectionType;
	@CsvBindByName
	private String dpi;

	private List<ReviewDto> reviews = new LinkedList<>();
}
