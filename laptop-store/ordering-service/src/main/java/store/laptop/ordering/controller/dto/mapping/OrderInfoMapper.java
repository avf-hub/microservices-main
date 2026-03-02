

package store.laptop.ordering.controller.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import store.laptop.ordering.domain.model.Order;
import store.laptop.ordering.shared.model.web.dto.OrderInfo;

@Mapper(componentModel = "spring")
public interface OrderInfoMapper {
	@Mapping(target = "status", source = "orderState")
	OrderInfo map(Order order);
}
