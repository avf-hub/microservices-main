

package store.laptop.ordering.controller.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.laptop.ordering.domain.model.Order;
import store.laptop.ordering.shared.model.web.dto.OrderData;

@Mapper(componentModel = "spring")
public interface OrderDataMapper {
	@Mapping(target = "orderId", source = "id")
	OrderData map(Order order);
}
