

package store.laptop.catalog.controller.dto.mapping;

import org.mapstruct.Mapper;
import store.laptop.catalog.domain.model.Laptop;
import store.laptop.catalog.shared.model.web.dto.LaptopInfo;

@Mapper(componentModel = "spring")
public interface LaptopInfoMapper {
	LaptopInfo map(Laptop laptop);
}
