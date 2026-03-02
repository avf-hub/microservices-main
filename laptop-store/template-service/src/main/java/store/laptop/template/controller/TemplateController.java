

package store.laptop.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.laptop.template.controller.dto.mapping.StubDataInfoMapper;
import store.laptop.template.domain.model.StubData;
import store.laptop.template.domain.service.StubDataProcessingService;
import store.laptop.template.shared.model.web.dto.StubDataInfo;

@RestController
@RequestMapping("/templates")
public class TemplateController {
	private final StubDataProcessingService processingService;
	private final StubDataInfoMapper mapper;

	@Autowired
	public TemplateController(StubDataProcessingService processingService,
	                          StubDataInfoMapper mapper) {
		this.processingService = processingService;
		this.mapper = mapper;
	}

	@GetMapping("/{stubId}")
	public StubDataInfo getStubData(@PathVariable Long stubId) {
		StubData stubData = processingService.getStabDataById(stubId);
		return mapper.map(stubData);
	}
}
