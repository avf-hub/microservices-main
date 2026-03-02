

package store.laptop.ordering.controller.dto.mapping;

import org.mapstruct.Mapper;
import store.laptop.ordering.domain.model.Order;
import store.laptop.ordering.shared.model.web.dto.PlaceOrderRequest;

@Mapper(componentModel = "spring")
public interface PlaceOrderRequestMapper {
	Order map(PlaceOrderRequest orderRequest);
}
