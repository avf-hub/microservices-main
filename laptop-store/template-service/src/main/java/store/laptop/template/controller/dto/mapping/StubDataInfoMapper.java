

package store.laptop.template.controller.dto.mapping;

import org.mapstruct.Mapper;
import store.laptop.template.domain.model.StubData;
import store.laptop.template.shared.model.web.dto.StubDataInfo;

@Mapper(componentModel = "spring")
public interface StubDataInfoMapper {
	StubDataInfo map(StubData stubData);
}
