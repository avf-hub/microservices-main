

package store.laptop.inventory.controller.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.laptop.inventory.domain.model.InventoryItem;
import store.laptop.inventory.shared.model.web.dto.ProductAvailabilityInfo;

@Mapper(componentModel = "spring")
public interface ProductAvailabilityInfoMapper {
	@Mapping(target = "inventoryId", source = "id")
	ProductAvailabilityInfo map(InventoryItem item);
}
